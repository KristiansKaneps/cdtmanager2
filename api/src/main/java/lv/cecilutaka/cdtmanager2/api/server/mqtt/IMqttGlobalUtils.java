package lv.cecilutaka.cdtmanager2.api.server.mqtt;

/**
 * Contains all MQTT utils.
 */
public interface IMqttGlobalUtils
{
	void initialize();

	IMqttRelayUtils getRelayUtils();
	IMqttBridgeUtils getBridgeUtils();
	IMqttFloodlightUtils getFloodlightUtils();

	int toRelayId(String mqttRelayId) throws MqttIdException;
	String toMqttRelayId(int relayId) throws MqttIdException;

	int toBridgeId(String mqttBridgeId) throws MqttIdException;
	String toMqttBridgeId(int bridgeId) throws MqttIdException;

	int toFloodlightId(String mqttFloodlightId) throws MqttIdException;
	String toMqttFloodlightId(int floodlightId) throws MqttIdException;
}
