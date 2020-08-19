package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.json.FirmwareInfoFactory;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.io.IOException;

@ConsumeMqttMessage(subscriptionId = 3)
public class MqttFloodlightFirmware extends MqttFloodlightMessageConsumer
{
	private final ObjectMapper mapper = new ObjectMapper();

	public MqttFloodlightFirmware()
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
			FirmwareInfoFactory factory = mapper.readValue(payload, FirmwareInfoFactory.class);

			if(registeredFloodlight.isEmpty()) return;

			IFirmwareInfo firmwareInfo = factory.build();

			IFloodlight floodlight = registeredFloodlight.get();
			floodlight.setFirmwareInfo(firmwareInfo);

			Log.d("Floodlight", "Floodlight #" + floodlight.getId() + " [firmwareInfo] = " + firmwareInfo);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
