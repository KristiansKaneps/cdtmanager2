package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * For testing purposes.
 */
@ConsumeMqttMessage(subscriptionId = 1337)
public class _MqttEverything implements MqttLocalMessageConsumer
{
	@Override
	public void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		String str;
		int payloadLength = 0;

		try
		{
			str = publish.toString();
			payloadLength = Integer.parseInt(str.substring(str.indexOf("payload=") + 8, str.indexOf("byte", str.indexOf("payload=") + 8)));
		}
		catch(Exception e)
		{
			try
			{
				payloadLength = publish.getPayloadAsBytes().length;
				str = "MqttPublish{topic=" + publish.getTopic() + ", payload=" + payloadLength + "byte, qos=" + publish.getQos().name() + ", retain=" + publish.isRetain() + "}";
			}
			catch(Exception ignored) { return; }
		}

		str = str.replaceFirst("MqttPublish", "").replaceFirst(" payload=\\d+byte, ", " payload[" + payloadLength + "b]=\"" + new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8) + "\", ");
		Log.d("===MQTT Everything===", "Received publish: " + str);
	}

	@Override
	public Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		return new Object[0];
	}
}
