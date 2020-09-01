package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.nio.charset.StandardCharsets;

@ConsumeMqttMessage(subscriptionId = 1)
public class MqttDeviceUptime extends MqttDeviceMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttId) throws Exception
	{
		byte[] payload = publish.getPayloadAsBytes();

		int uptimeInSeconds = Integer.parseInt(new String(payload, StandardCharsets.UTF_8));

		RegistryValue<DeviceType> regType = server.getMqttDeviceTypeRegistry().get(mqttId);

		if(regType.isEmpty())
		{
			Log.d("MQTT Device", "Device #" + mqttId + " [uptime] = " + uptimeInSeconds + "s");
			return;
		}

		DeviceType type = regType.get();

		Log.d("MQTT Device", "Device (type=" + type + ") #" + mqttId + " [uptime] = " + uptimeInSeconds + "s");

		switch(type)
		{
			case RELAY:
			{
				RegistryValue<IRelay> r = server.getRelayRegistry().get(server.getMqttUtils().toRelayId(mqttId));
				if (!r.isEmpty()) r.get().setUptime(uptimeInSeconds);
			} break;
			case BRIDGE:
			{
				RegistryValue<IBridge> r = server.getBridgeRegistry().get(server.getMqttUtils().toBridgeId(mqttId));
				if(!r.isEmpty()) r.get().setUptime(uptimeInSeconds);
			}  break;
			case MONO_FLOODLIGHT:
			case RGB_FLOODLIGHT:
			case RGB_MATRIX:
			{
				RegistryValue<IFloodlight> r = server.getFloodlightRegistry().get(server.getMqttUtils().toFloodlightId(mqttId));
				if(!r.isEmpty()) r.get().setUptime(uptimeInSeconds);
			} break;
			default:
				return;
		}
	}
}