package lv.cecilutaka.cdtmanager2.server.device.bridge.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.util.List;

public abstract class MqttBridgeMessageConsumer implements MqttLocalMessageConsumer
{
	@Override
	public final void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		if(parsedTopicLevels == null)
		{
			Log.d("MQTT Bridge", "Failed to consume bridge's published message as there is no bridge ID present.");
			return;
		}

		int bridgeId = (Integer) parsedTopicLevels[1];
		String mqttBridgeId = (String) parsedTopicLevels[0];

		try
		{
			this.consume(server, publish, mqttBridgeId, bridgeId, server.getBridgeRegistry().get(bridgeId));
		}
		catch(NumberFormatException e)
		{
			Log.w("MQTT Bridge", "Failed to parse integer value.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void consume(Server server, Mqtt5Publish publish, String mqttBridgeId, int bridgeId, RegistryValue<IBridge> registeredBridge) throws Exception;

	@Override
	public final Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		if(topicLevels.size() < 3) return null;

		try
		{
			String mqttBridgeId = topicLevels.get(2);
			int bridgeId = server.getMqttBridgeUtils().toBridgeId(mqttBridgeId);
			return new Object[]{ mqttBridgeId, bridgeId };
		}
		catch(MqttIdException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}