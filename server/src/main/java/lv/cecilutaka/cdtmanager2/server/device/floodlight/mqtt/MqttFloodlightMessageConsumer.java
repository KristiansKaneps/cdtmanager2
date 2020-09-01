package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.util.List;

public abstract class MqttFloodlightMessageConsumer implements MqttLocalMessageConsumer
{
	@Override
	public final void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		if(parsedTopicLevels == null)
		{
			Log.d("MQTT Floodlight", "Failed to consume floodlight's published message as there is no floodlight ID present.");
			return;
		}

		int floodlightId = (Integer) parsedTopicLevels[1];
		String mqttFloodlightId = (String) parsedTopicLevels[0];

		try
		{
			this.consume(server, publish, mqttFloodlightId, floodlightId, server.getFloodlightRegistry().get(floodlightId));
		}
		catch(NumberFormatException e)
		{
			Log.w("MQTT Floodlight", "Failed to parse integer value.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void consume(Server server, Mqtt5Publish publish, String mqttFloodlightId, int floodlightId, RegistryValue<IFloodlight> registeredFloodlight) throws Exception;

	@Override
	public final Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		if(topicLevels.size() < 3) return null;

		try
		{
			String mqttFloodlightId = topicLevels.get(2);
			int floodlightId = server.getMqttUtils().toFloodlightId(mqttFloodlightId);
			return new Object[]{ mqttFloodlightId, floodlightId };
		}
		catch(MqttIdException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
