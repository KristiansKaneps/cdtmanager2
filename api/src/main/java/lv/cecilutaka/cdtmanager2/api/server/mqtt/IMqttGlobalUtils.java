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

	int toRelayId(int mqttRelayId) throws MqttIdException;
	int toMqttRelayId(int relayId) throws MqttIdException;

	int toBridgeId(int mqttBridgeId) throws MqttIdException;
	int toMqttBridgeId(int bridgeId) throws MqttIdException;

	int toFloodlightId(int mqttFloodlightId) throws MqttIdException;
	int toMqttFloodlightId(int floodlightId) throws MqttIdException;
}
