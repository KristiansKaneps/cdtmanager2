package lv.cecilutaka.cdtmanager2.server.mqtt.utils;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.log.Log;

import java.util.HashMap;
import java.util.Map;

public class MqttFloodlightUtils implements IMqttFloodlightUtils
{
	private final Object lock = new Object();

	private static volatile int genId = 0;

	/**
	 * MQTT ID is chip ID in HEX
	 */
	private final Map<String, Integer> mqttId_to_id = new HashMap<>();
	private final Map<Integer, String> id_to_mqttId = new HashMap<>();

	@Override
	public void initialize()
	{
		// TODO: Store ID mappings to a database or file.
	}

	public int toFloodlightId(String mqttFloodlightId) throws MqttIdException
	{
		synchronized (lock)
		{
			if(mqttId_to_id.containsKey(mqttFloodlightId))
				return mqttId_to_id.get(mqttFloodlightId);
			int floodlightId = genId++;
			mqttId_to_id.put(mqttFloodlightId, floodlightId);
			id_to_mqttId.put((Integer) floodlightId, mqttFloodlightId);
			Log.i("MQTT Floodlight ID", "MQTT ID '" + mqttFloodlightId + "' mapped to floodlight ID '" + floodlightId + "'");
			return floodlightId;
		}
	}

	public String toMqttFloodlightId(int floodlightId) throws MqttIdException
	{
		synchronized (lock)
		{
			if(id_to_mqttId.containsKey((Integer) floodlightId))
				return id_to_mqttId.get((Integer) floodlightId);
			throw new MqttIdException("No MQTT ID found for floodlight #" + floodlightId);
		}
	}
}
