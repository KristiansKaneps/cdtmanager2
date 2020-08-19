package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.nio.charset.StandardCharsets;

@ConsumeMqttMessage(subscriptionId = 5)
public class MqttFloodlightUptime extends MqttFloodlightMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttFloodlightId, int floodlightId, RegistryValue<IFloodlight> registeredFloodlight) throws Exception
	{
		byte[] payload = publish.getPayloadAsBytes();

		if(registeredFloodlight.isEmpty()) return;

		int uptimeInSeconds = Integer.parseInt(new String(payload, StandardCharsets.UTF_8));

		registeredFloodlight.get().setUptime(uptimeInSeconds);
		Log.d("Floodlight", "Floodlight #" + floodlightId + " [uptime] = " + uptimeInSeconds + "s");
	}
}
