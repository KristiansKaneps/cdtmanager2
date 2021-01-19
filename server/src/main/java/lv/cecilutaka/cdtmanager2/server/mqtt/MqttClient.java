package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttConnectionListener;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttClient;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttQos;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode.SUCCESS;

public class MqttClient implements IMqttClient
{
	private final List<IMqttConnectionListener> connectionListeners = new ArrayList<>();
	private final List<String> broadcastTopicLocations = new ArrayList<>();

	private Mqtt5AsyncClient mqtt;

	private volatile boolean connected = false, connecting = false;

	private final Server server;

	public MqttClient(Server server)
	{
		this.server = server;

		INetworkMqttConfig config = server.getNetworkMqttConfig();

		Mqtt5ClientBuilder builder = com.hivemq.client.mqtt.MqttClient.builder()
		                                      .identifier(UUID.randomUUID().toString())
		                                      .serverHost(config.getHostname())
		                                      .serverPort(config.getPort())
		                                      .automaticReconnect()
		                                        .initialDelay(config.getAutoReconnectMinDelay(), TimeUnit.SECONDS)
		                                        .maxDelay(config.getAutoReconnectMaxDelay(), TimeUnit.SECONDS)
		                                        .applyAutomaticReconnect()
		                                      .addConnectedListener(this::onConnected)
		                                      .addDisconnectedListener(this::onDisconnected)
		                                      .useMqttVersion5();
		if (config.getSsl()) builder = builder.sslConfig()
		                                        .handshakeTimeout(5, TimeUnit.SECONDS)
		                                        .cipherSuites(null) // use netty's default cipher suites
		                                        .protocols(config.getSslProtocols())
		                                        .applySslConfig();

		mqtt = builder.buildAsync();
	}

	public Mqtt5AsyncClient mqtt()
	{
		return mqtt;
	}

	private synchronized void _publish0(
			String topic,
			com.hivemq.client.mqtt.datatypes.MqttQos qos,
			ByteBuffer payload,
			boolean retain
	) {
		if(!connected) return;
		mqtt.publishWith()
		    .topic(topic)
		    .qos(qos)
		    .payload(payload)
		    .retain(retain)
		    .send();
	}

	private synchronized void _publish0(
			String topic,
			com.hivemq.client.mqtt.datatypes.MqttQos qos,
			byte[] payload,
			boolean retain
	) {
		if(!connected) return;
		mqtt.publishWith()
		    .topic(topic)
		    .qos(qos)
		    .payload(payload)
		    .retain(retain)
		    .send();
	}

	private synchronized void _subscribe0(
			String topicFilter,
			com.hivemq.client.mqtt.datatypes.MqttQos qos,
			final int subscriptionId
	) {
		// Fetch messages before subscription.
		server.getMqttMessageConsumer().fetchUnsolicitedMessages(mqtt);

		if(!connected) return;
		Log.i("MQTT", "Subscribing to '" + topicFilter + "' with QoS of " + qos.name());

		mqtt.subscribeWith()
		    .topicFilter(topicFilter)
		    .qos(qos)
		    .callback(publish -> server.getMqttMessageConsumer().handleSubscribedMessage(publish, subscriptionId))
		    .send();
	}

	private synchronized void _unsubscribe0(
			String topicFilter
	) {
		if(!connected) return;
		mqtt.unsubscribeWith().topicFilter(topicFilter).send();
	}

	private synchronized void _broadcast0(String topic, com.hivemq.client.mqtt.datatypes.MqttQos qos, ByteBuffer payload, boolean retain)
	{
		for(String broadcastTopicLocation : broadcastTopicLocations)
			mqtt.publishWith()
			    .topic(broadcastTopicLocation + "/" + topic)
			    .payload(payload)
			    .qos(qos)
			    .retain(retain)
			    .send();
	}

	private synchronized void _broadcast0(String topic, com.hivemq.client.mqtt.datatypes.MqttQos qos, byte[] payload, boolean retain)
	{
		for(String broadcastTopicLocation : broadcastTopicLocations)
			mqtt.publishWith()
			    .topic(broadcastTopicLocation + "/" + topic)
			    .payload(payload)
			    .qos(qos)
			    .retain(retain)
			    .send();
	}

	@Override
	public void publish(String topic, MqttQos qos, ByteBuffer payload, boolean retain)
	{
		_publish0(topic, Objects.requireNonNull(com.hivemq.client.mqtt.datatypes.MqttQos.fromCode(qos.ordinal())), payload, retain);
	}

	@Override
	public void publish(String topic, MqttQos qos, byte[] payload, boolean retain)
	{
		_publish0(topic, Objects.requireNonNull(com.hivemq.client.mqtt.datatypes.MqttQos.fromCode(qos.ordinal())), payload, retain);
	}

	@Override
	public void subscribe(String topicFilter, MqttQos qos, int subscriptionId)
	{
		_subscribe0(topicFilter, Objects.requireNonNull(com.hivemq.client.mqtt.datatypes.MqttQos.fromCode(qos.ordinal())), subscriptionId);
	}

	@Override
	public void unsubscribe(String topicFilter)
	{
		_unsubscribe0(topicFilter);
	}

	@Override
	public void broadcast(String topic, MqttQos qos, ByteBuffer payload, boolean retain)
	{
		_broadcast0(topic, Objects.requireNonNull(com.hivemq.client.mqtt.datatypes.MqttQos.fromCode(qos.ordinal())), payload, retain);
	}

	@Override
	public void broadcast(String topic, MqttQos qos, byte[] payload, boolean retain)
	{
		_broadcast0(topic, Objects.requireNonNull(com.hivemq.client.mqtt.datatypes.MqttQos.fromCode(qos.ordinal())), payload, retain);
	}

	@Override
	public void broadcastConnected()
	{
		broadcast("cm", MqttQos.EXACTLY_ONCE, "1".getBytes(StandardCharsets.UTF_8), false);
	}

	@Override
	public synchronized void addBroadcastTopicLocation(String broadcastTopicLocation)
	{
		if(!broadcastTopicLocations.contains(broadcastTopicLocation))
			broadcastTopicLocations.add(broadcastTopicLocation);
	}

	@Override
	public synchronized void removeBroadcastTopicLocation(String broadcastTopicLocation)
	{
		broadcastTopicLocations.remove(broadcastTopicLocation);
	}

	@Override
	public synchronized void connect()
	{
		if(connected || connecting) return;
		connecting = true;
		mqtt.connectWith()
		    .simpleAuth()
		      .username(server.getNetworkMqttConfig().getUser())
		      .password(server.getNetworkMqttConfig().getPassword().getBytes(StandardCharsets.UTF_8))
		      .applySimpleAuth()
		    .noSessionExpiry()
		    .keepAlive(65535)
		    .noSessionExpiry()
		    //  .willPublish()
		    //  .topic("fi/cm")
		    //  .payload("1".getBytes(StandardCharsets.UTF_8))
		    //  .payloadFormatIndicator(Mqtt5PayloadFormatIndicator.UTF_8)
		    //  .qos(com.hivemq.client.mqtt.datatypes.MqttQos.EXACTLY_ONCE)
		    //  .retain(false)
		    //  .applyWillPublish()
		    .send()
		    .whenCompleteAsync((ack, cause) -> {
				Mqtt5ConnAckReasonCode reason = ack.getReasonCode();
				Log.i("MQTT", "Connection status: " + reason.name());
				if(cause != null) cause.printStackTrace();
				if(reason == SUCCESS)
					broadcastConnected();
		});
	}

	@Override
	public synchronized void disconnect()
	{
		if(!connected) return;
		mqtt.disconnect();
	}

	@Override
	public synchronized void reauth()
	{
		if(!connected) return;
		mqtt.reauth();
	}

	@Override
	public boolean isConnected()
	{
		return connected;
	}

	@Override
	public void addConnectionListener(IMqttConnectionListener listener)
	{
		synchronized (connectionListeners)
		{
			connectionListeners.add(listener);
		}
	}

	@Override
	public void removeConnectionListener(IMqttConnectionListener listener)
	{
		synchronized (connectionListeners)
		{
			connectionListeners.remove(listener);
		}
	}

	private void onConnected(MqttClientConnectedContext context)
	{
		connected = true;
		connecting = false;
		synchronized (connectionListeners)
		{
			connectionListeners.forEach(listener -> listener.onConnected(this));
		}
		Log.i("MQTT", "Connected to " + server.getNetworkMqttConfig().getHostname() + ":" + server.getNetworkMqttConfig().getPort());
	}

	private void onDisconnected(MqttClientDisconnectedContext context)
	{
		connected = false;
		synchronized (connectionListeners)
		{
			connectionListeners.forEach(listener -> listener.onDisconnected(this));
		}
		Log.i("MQTT", "Disconnected from " + server.getNetworkMqttConfig().getHostname() + ":" + server.getNetworkMqttConfig().getPort());
		Log.i("MQTT", " Context cause: " + context.getCause());
	}
}
