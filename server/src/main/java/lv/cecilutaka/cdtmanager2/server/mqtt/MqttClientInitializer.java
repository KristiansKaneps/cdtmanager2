package lv.cecilutaka.cdtmanager2.server.mqtt;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttClient;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttConnectionListener;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttQos;
import lv.cecilutaka.cdtmanager2.server.Server;

/**
 * Subscribes to mandatory topics
 */
public class MqttClientInitializer implements IMqttConnectionListener
{
	private final Server server;

	public MqttClientInitializer(Server server)
	{
		this.server = server;
	}

	@Override
	public void onConnected(IMqttClient client)
	{
		client.addBroadcastTopicLocation("fi/brdc");

		client.subscribe("fo/+/c", MqttQos.EXACTLY_ONCE, 0); // connect
		client.subscribe("fo/+/ac", MqttQos.EXACTLY_ONCE, 0); // same as "fo/+/c" but a device was already connected
		client.subscribe("fo/+/mc", MqttQos.EXACTLY_ONCE, 0); // same as "fo/+/c" but a device connected through another mesh node

		client.subscribe("fo/bssid/+", MqttQos.AT_LEAST_ONCE, 1);

		client.subscribe("fo/+/msg", MqttQos.AT_LEAST_ONCE, 2);
		client.subscribe("fo/+/fw", MqttQos.AT_LEAST_ONCE, 3);
		client.subscribe("fo/+/f", MqttQos.AT_LEAST_ONCE, 4);
		client.subscribe("fo/+/u", MqttQos.AT_LEAST_ONCE, 5);
		client.subscribe("fo/+/gc", MqttQos.AT_LEAST_ONCE, 6);

		//client.subscribe("#", MqttQos.AT_LEAST_ONCE, 1337); // for testing purposes
	}

	@Override
	public void onDisconnected(IMqttClient client)
	{

	}
}
