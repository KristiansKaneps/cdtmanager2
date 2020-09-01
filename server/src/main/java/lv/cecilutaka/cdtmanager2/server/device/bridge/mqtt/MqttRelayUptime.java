package lv.cecilutaka.cdtmanager2.server.device.bridge.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.nio.charset.StandardCharsets;

@ConsumeMqttMessage(subscriptionId = 3)
public class MqttRelayUptime extends MqttRelayMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttRelayId, int relayId, RegistryValue<IRelay> registeredRelay) throws Exception
	{
		if(registeredRelay.isEmpty()) return;

		byte[] payload = publish.getPayloadAsBytes();

		int uptimeInSeconds = Integer.parseInt(new String(payload, StandardCharsets.UTF_8));

		registeredRelay.get().setUptime(uptimeInSeconds);
		Log.d("Relay", "Relay #" + relayId + " [uptime] = " + uptimeInSeconds + "s");
	}
}