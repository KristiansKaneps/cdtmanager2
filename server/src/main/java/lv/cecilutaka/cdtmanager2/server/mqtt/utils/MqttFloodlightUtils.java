package lv.cecilutaka.cdtmanager2.server.mqtt.utils;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;

public class MqttFloodlightUtils extends MqttDeviceUtils implements IMqttFloodlightUtils
{
	@Override
	public void initialize()
	{
		initialize0();
	}

	public int toFloodlightId(int mqttFloodlightId) throws MqttIdException
	{
		return toId0(mqttFloodlightId);
	}

	public int toMqttFloodlightId(int floodlightId) throws MqttIdException
	{
		return toMqttId0(floodlightId);
	}
}
