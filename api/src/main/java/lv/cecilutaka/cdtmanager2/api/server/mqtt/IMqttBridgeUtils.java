package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttBridgeUtils
{
	void initialize();

	int toBridgeId(String mqttBridgeId) throws MqttIdException;
	String toMqttBridgeId(int bridgeId) throws MqttIdException;
}
