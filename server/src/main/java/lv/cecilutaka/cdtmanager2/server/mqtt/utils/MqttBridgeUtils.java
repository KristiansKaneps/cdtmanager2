package lv.cecilutaka.cdtmanager2.server.mqtt.utils;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttBridgeUtils;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;

public class MqttBridgeUtils extends MqttDeviceUtils implements IMqttBridgeUtils
{
	@Override
	public void initialize()
	{
		initialize0();
	}

	@Override
	public int toBridgeId(String mqttBridgeId) throws MqttIdException
	{
		return toId0(mqttBridgeId);
	}

	@Override
	public String toMqttBridgeId(int bridgeId) throws MqttIdException
	{
		return toMqttId0(bridgeId);
	}
}
