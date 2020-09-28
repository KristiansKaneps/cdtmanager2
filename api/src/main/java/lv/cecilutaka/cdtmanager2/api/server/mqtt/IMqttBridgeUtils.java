package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttBridgeUtils
{
	void initialize();

	int toBridgeId(int mqttBridgeId) throws MqttIdException;
	int toMqttBridgeId(int bridgeId) throws MqttIdException;
}
