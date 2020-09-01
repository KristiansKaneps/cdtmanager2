package lv.cecilutaka.cdtmanager2.server.device.bridge.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.nio.charset.StandardCharsets;

@ConsumeMqttMessage(subscriptionId = 2)
public class MqttBridgeUptime extends MqttBridgeMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttBridgeId, int bridgeId, RegistryValue<IBridge> registeredBridge) throws Exception
	{
		if(registeredBridge.isEmpty()) return;

		byte[] payload = publish.getPayloadAsBytes();

		int uptimeInSeconds = Integer.parseInt(new String(payload, StandardCharsets.UTF_8));

		registeredBridge.get().setUptime(uptimeInSeconds);
		Log.d("Bridge", "Bridge #" + bridgeId + " [uptime] = " + uptimeInSeconds + "s");
	}
}