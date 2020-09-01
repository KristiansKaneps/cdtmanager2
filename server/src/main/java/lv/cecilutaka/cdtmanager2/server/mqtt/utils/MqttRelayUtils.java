package lv.cecilutaka.cdtmanager2.server.mqtt.utils;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttRelayUtils;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;

public class MqttRelayUtils extends MqttDeviceUtils implements IMqttRelayUtils
{
	@Override
	public void initialize()
	{
		initialize0();
	}

	@Override
	public int toRelayId(String mqttRelayId) throws MqttIdException
	{
		return toId0(mqttRelayId);
	}

	@Override
	public String toMqttRelayId(int relayId) throws MqttIdException
	{
		return toMqttId0(relayId);
	}
}
