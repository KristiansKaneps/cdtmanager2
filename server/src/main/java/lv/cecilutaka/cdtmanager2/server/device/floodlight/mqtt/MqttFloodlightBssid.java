package lv.cecilutaka.cdtmanager2.server.device.floodlight.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.IBssidDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.device.MacAddress;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttLocalMessageConsumer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ConsumeMqttMessage(subscriptionId = 1)
public class MqttFloodlightBssid implements MqttLocalMessageConsumer
{
	@Override
	public void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels)
	{
		if(parsedTopicLevels == null)
		{
			Log.d("MQTT Floodlight", "Failed to consume floodlight's published message as there is no floodlight ID present.");
			return;
		}

		byte[] payload = publish.getPayloadAsBytes();

		if(payload.length == 0) return;

		String bssid = new String(payload, StandardCharsets.UTF_8);
		//String mqttFloodlightId = (String) parsedTopicLevels[0];
		int floodlightId = (int) parsedTopicLevels[1];

		RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
		if(regValue.isEmpty()) return;

		IFloodlight iFloodlight = regValue.get();

		if(!(iFloodlight instanceof IBssidDevice)) return;

		IBssidDevice iDevice = (IBssidDevice) iFloodlight;

		try
		{
			iDevice.setBssid(new MacAddress(bssid));
			Log.i("Floodlight BSSID", "Floodlight #" + floodlightId + " is on BSSID: " + bssid);
		}
		catch(IllegalArgumentException e)
		{
			iDevice.setBssid(null);
			Log.w("Floodlight BSSID", "Floodlight #" + floodlightId + " has an invalid BSSID!");
		}
	}

	@Override
	public Object[] parseTopicLevels(Server server, List<String> topicLevels)
	{
		if(topicLevels.size() < 3) return null;

		try
		{
			String mqttFloodlightId = topicLevels.get(2);
			int floodlightId = server.getMqttFloodlightUtils().toFloodlightId(mqttFloodlightId);
			return new Object[]{ mqttFloodlightId, floodlightId };
		}
		catch(MqttIdException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
