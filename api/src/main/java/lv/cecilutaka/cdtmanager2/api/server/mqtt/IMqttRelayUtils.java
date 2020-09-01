package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttRelayUtils
{
	void initialize();

	int toRelayId(String mqttRelayId) throws MqttIdException;
	String toMqttRelayId(int relayId) throws MqttIdException;
}
