package lv.cecilutaka.cdtmanager2.server;

import com.typesafe.config.Config;

import lv.cecilutaka.cdtmanager2.api.server.IServer;
import lv.cecilutaka.cdtmanager2.common.StartStopImpl;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.common.threading.Loop;
import lv.cecilutaka.cdtmanager2.server.config.ConfigLoader;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.server.config.NetworkRestConfig;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClient;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClientInitializer;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttMessageConsumer;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.server.registry.FloodlightRegistry;

public class Server extends StartStopImpl implements IServer
{
	private FloodlightRegistry floodlightRegistry;

	private Loop mqttMessageConsumerLoop;
	private MqttMessageConsumer mqttMessageConsumer;
	private MqttClient mqttClient;
	private MqttFloodlightUtils mqttFloodlightUtils;

	private NetworkMqttConfig netMqttConfig;
	private NetworkRestConfig netRestConfig;

	private final ConfigLoader configLoader;

	public Server()
	{
		this.configLoader = new ConfigLoader();
	}

	@Override
	protected void onStart()
	{
		Log.i("Starting...");
		long start = System.currentTimeMillis();

		floodlightRegistry = new FloodlightRegistry();

		mqttFloodlightUtils = new MqttFloodlightUtils();

		Log.i("Loading config...");
		Config networkConfig = configLoader.load("network.conf");

		netMqttConfig = configLoader.load(networkConfig.getConfig("mqtt"), NetworkMqttConfig.class);
		netRestConfig = configLoader.load(networkConfig.getConfig("rest"), NetworkRestConfig.class);
		Log.i("Config loaded:");
		Log.i("MQTT Server Address = " + netMqttConfig.getIp() + ":" + netMqttConfig.getPort());
		Log.i("MQTT User = " + netMqttConfig.getUser());
		Log.i("MQTT Use SSL = " + netMqttConfig.getSsl());
		if(netMqttConfig.getSsl()) Log.i("MQTT SSL Protocols = " + netMqttConfig.getSslProtocols().toString());
		Log.i("REST Listening on = " + netRestConfig.getIp() + ":" + netRestConfig.getPort());

		mqttClient = new MqttClient(this);
		mqttClient.addConnectionListener(new MqttClientInitializer(this));

		mqttFloodlightUtils.initialize();

		mqttMessageConsumerLoop = new Loop(mqttMessageConsumer = new MqttMessageConsumer(this, false), "CDTManager2 MQTT Message Consumer");

		mqttMessageConsumerLoop.startAsync();
		mqttClient.connect();

		Log.i("Started in " + (System.currentTimeMillis() - start) + " ms.");
	}

	@Override
	protected void onStop()
	{
		Log.i("Stopping...");

		mqttClient.disconnect();
		mqttMessageConsumerLoop.stop();

		Log.i("Stopped.");
	}

	@Override
	public NetworkMqttConfig getNetworkMqttConfig()
	{
		return netMqttConfig;
	}

	@Override
	public NetworkRestConfig getNetworkRestConfig()
	{
		return netRestConfig;
	}

	@Override
	public MqttClient getMqttClient()
	{
		return mqttClient;
	}

	@Override
	public MqttMessageConsumer getMqttMessageConsumer()
	{
		return mqttMessageConsumer;
	}

	@Override
	public MqttFloodlightUtils getMqttFloodlightUtils()
	{
		return mqttFloodlightUtils;
	}

	@Override
	public FloodlightRegistry getFloodlightRegistry()
	{
		return floodlightRegistry;
	}
}
