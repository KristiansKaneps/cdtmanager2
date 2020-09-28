package lv.cecilutaka.cdtmanager2.server.mqtt.utils;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.log.Log;

import java.util.HashMap;
import java.util.Map;

public abstract class MqttDeviceUtils
{
	private final Object lock = new Object();

	private static volatile int genId = 0;

	/**
	 * MQTT ID is chip ID in HEX
	 */
	private final Map<Integer, Integer> mqttId_to_id = new HashMap<>();
	private final Map<Integer, Integer> id_to_mqttId = new HashMap<>();

	protected MqttDeviceUtils() { }

	protected void initialize0()
	{
		// TODO: Store ID mappings to a database or file.
	}

	protected int toId0(int mqttId) throws MqttIdException
	{
		synchronized (lock)
		{
			if(mqttId_to_id.containsKey(mqttId))
				return mqttId_to_id.get(mqttId);
			int id = genId++;
			mqttId_to_id.put(mqttId, id);
			id_to_mqttId.put(id, mqttId);
			Log.i("MQTT Device ID", "MQTT ID '" + mqttId + "' mapped to device ID '" + id + "'");
			return id;
		}
	}

	protected int toMqttId0(int id) throws MqttIdException
	{
		synchronized (lock)
		{
			if(id_to_mqttId.containsKey(id))
				return id_to_mqttId.get(id);
			throw new MqttIdException("No MQTT ID found for floodlight #" + id);
		}
	}
}