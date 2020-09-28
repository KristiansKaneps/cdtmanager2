package lv.cecilutaka.cdtmanager2.server.device.bridge.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.util.List;

public abstract class MqttRelayMessageConsumer implements MqttLocalMessageConsumer
{
	@Override
	public final void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		if(parsedTopicLevels == null)
		{
			Log.d("MQTT Relay", "Failed to consume relay's published message as there is no relay ID present.");
			return;
		}

		int relayId = (Integer) parsedTopicLevels[1];
		String mqttRelayId = (String) parsedTopicLevels[0];

		try
		{
			this.consume(server, publish, mqttRelayId, relayId, server.getRelayRegistry().get(relayId));
		}
		catch(NumberFormatException e)
		{
			Log.w("MQTT Relay", "Failed to parse integer value.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void consume(Server server, Mqtt5Publish publish, String mqttRelayId, int relayId, RegistryValue<IRelay> registeredRelay) throws Exception;

	@Override
	public final Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		if(topicLevels.size() < 3) return null;

		try
		{
			int mqttRelayId = Integer.parseInt(topicLevels.get(2));
			int relayId = server.getMqttUtils().toRelayId(mqttRelayId);
			return new Object[]{ mqttRelayId, relayId };
		}
		catch(MqttIdException | NumberFormatException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}