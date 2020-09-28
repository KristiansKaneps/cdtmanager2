package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttFloodlightUtils
{
	void initialize();

	int toFloodlightId(int mqttFloodlightId) throws MqttIdException;
	int toMqttFloodlightId(int floodlightId) throws MqttIdException;
}
