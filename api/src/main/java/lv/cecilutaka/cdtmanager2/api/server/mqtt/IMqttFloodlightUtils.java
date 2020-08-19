package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttFloodlightUtils
{
	void initialize();

	int toFloodlightId(String mqttFloodlightId) throws MqttIdException;
	String toMqttFloodlightId(int floodlightId) throws MqttIdException;
}
