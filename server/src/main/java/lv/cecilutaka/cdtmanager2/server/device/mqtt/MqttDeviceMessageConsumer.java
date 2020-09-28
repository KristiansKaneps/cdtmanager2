package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.util.List;

public abstract class MqttDeviceMessageConsumer implements MqttLocalMessageConsumer
{
	@Override
	public final void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		if(parsedTopicLevels == null)
		{
			Log.d("MQTT Device", "Failed to consume device's published message as there is no device ID present.");
			return;
		}



		try
		{
			int mqttId = Integer.parseInt((String) parsedTopicLevels[0]);
			this.consume(server, publish, mqttId);
		}
		catch(NumberFormatException e)
		{
			Log.w("MQTT Device", "Failed to parse integer value.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void consume(Server server, Mqtt5Publish publish, int mqttId) throws Exception;

	@Override
	public final Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		if(topicLevels.size() > 2)
			return new Object[] { topicLevels.get(2) };
		return null;
	}
}