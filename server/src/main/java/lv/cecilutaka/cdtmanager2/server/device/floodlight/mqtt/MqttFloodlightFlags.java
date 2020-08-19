package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

@ConsumeMqttMessage(subscriptionId = 4)
public class MqttFloodlightFlags extends MqttFloodlightMessageConsumer
{
	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttFloodlightId, int floodlightId, RegistryValue<IFloodlight> registeredFloodlight)
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length == 0 || registeredFloodlight.isEmpty()) return;

		boolean turnedOn = (payload[0] & 0x01) != 0;

		IFloodlight floodlight = registeredFloodlight.get();

		floodlight.setTurnedOn(turnedOn);

		Log.d("Floodlight", "Floodlight #" + floodlight.getId() + " [flags] = " + String.format("0x%04X", payload[0]));
	}
}
