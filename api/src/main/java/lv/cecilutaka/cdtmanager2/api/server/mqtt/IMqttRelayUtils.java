package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttRelayUtils
{
	void initialize();

	int toRelayId(int mqttRelayId) throws MqttIdException;
	int toMqttRelayId(int relayId) throws MqttIdException;
}
