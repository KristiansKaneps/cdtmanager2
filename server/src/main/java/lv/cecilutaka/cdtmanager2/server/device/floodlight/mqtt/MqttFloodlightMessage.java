package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.IDeviceMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.json.DeviceMessageFactory;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.io.IOException;

@ConsumeMqttMessage(subscriptionId = 2)
public class MqttFloodlightMessage extends MqttFloodlightMessageConsumer
{
	private final ObjectMapper mapper = new ObjectMapper();

	public MqttFloodlightMessage()
	{
		mapper.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Override
	protected void consume(Server server, Mqtt5Publish publish, String mqttFloodlightId, int floodlightId, RegistryValue<IFloodlight> registeredFloodlight)
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length == 0) return;

		try
		{
			DeviceMessageFactory factory = mapper.readValue(payload, DeviceMessageFactory.class);

			if(registeredFloodlight.isEmpty()) return;

			IDeviceMessage message = factory.build();

			IFloodlight floodlight = registeredFloodlight.get();
			floodlight.setLastMessage(message);

			Log.d("Floodlight", "Message from floodlight #" + floodlight.getId() + ": " + message.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
