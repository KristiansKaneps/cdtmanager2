package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

@ConsumeMqttMessage(subscriptionId = 6)
public class MqttFloodlightColor extends MqttFloodlightMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttFloodlightId, int floodlightId, RegistryValue<IFloodlight> registeredFloodlight)
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length < 4) return;

		if(registeredFloodlight.isEmpty()) return;

		DeviceType deviceType = DeviceType.fromInstance(registeredFloodlight.get());

		if(DeviceType.RGB_FLOODLIGHT != deviceType)
		{
			Log.w("Floodlight", "Floodlight #" + floodlightId + " tried to send color data, but it's type is " + deviceType.name() + ". Ignoring this message.");
			return;
		}

		int color = (payload[0] & 0xff) << 24 | (payload[1] & 0xff) << 16 | (payload[2] & 0xff) << 8 | (payload[4] & 0xff);

		((IRGBFloodlight) registeredFloodlight.get()).setColor(color);
		Log.d("Floodlight", "Floodlight #" + floodlightId + " [color] = 0x" + Integer.toHexString(color));
	}
}
