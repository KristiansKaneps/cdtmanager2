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
		client.addBroadcastTopicLocation("fi/bc");

		client.subscribe("fo/fw/+", MqttQos.EXACTLY_ONCE, 0); // connect or firmware (global)

		client.subscribe("fo/u/+", MqttQos.EXACTLY_ONCE, 1); // uptime (floodlight)
		client.subscribe("bo/u/+", MqttQos.EXACTLY_ONCE, 2); // uptime (bridge)
		client.subscribe("ro/u/+", MqttQos.EXACTLY_ONCE, 3); // uptime (relay)

		client.subscribe("fo/c/+", MqttQos.EXACTLY_ONCE, 10); // color (floodlight)


		//client.subscribe("#", MqttQos.AT_LEAST_ONCE, 1337); // for testing purposes
	}

	@Override
	public void onDisconnected(IMqttClient client)
	{

	}
}
