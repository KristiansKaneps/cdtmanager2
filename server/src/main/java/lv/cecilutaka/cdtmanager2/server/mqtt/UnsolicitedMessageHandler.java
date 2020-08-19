package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;

public class UnsolicitedMessageHandler
{
	private final MqttMessageConsumer mqttMessageConsumer;

	protected UnsolicitedMessageHandler(MqttMessageConsumer mqttMessageConsumer)
	{
		this.mqttMessageConsumer = mqttMessageConsumer;
	}

	private Server server()
	{
		return mqttMessageConsumer.server;
	}

	public void handle(Mqtt5Publish publish)
	{
		Log.d("MQTT", "Received unsolicited publish on topic '" + publish.getTopic() + "'");
	}
}
