package lv.cecilutaka.cdtmanager2.api.server.mqtt;

import java.nio.ByteBuffer;

public interface IMqttClient
{
	void publish(String topic, MqttQos qos, ByteBuffer payload, boolean retain);
	void publish(String topic, MqttQos qos, byte[] payload, boolean retain);

	/**
	 * Use {@code addBroadcastTopicLocation(String topicLocation)} to add broadcast topics.
	 */
	void broadcast(String topic, MqttQos qos, ByteBuffer payload, boolean retain);
	void broadcast(String topic, MqttQos qos, byte[] payload, boolean retain);

	/**
	 * Broadcasts to the MQTT broker that this client is connected (only if there is at least one broadcast topic location added)
	 */
	void broadcastConnected();

	/**
	 * @param topicFilter - topic filter
	 * @param qos - quality of service
	 * @param subscriptionId - local subscription id that should be passed back in callback (nothing to do with MQTT protocol)
	 */
	void subscribe(String topicFilter, MqttQos qos, int subscriptionId);
	void unsubscribe(String topicFilter);

	void addBroadcastTopicLocation(String broadcastTopicLocation);
	void removeBroadcastTopicLocation(String broadcastTopicLocation);

	void connect();
	void disconnect();

	/**
	 * Only MQTT 5
	 */
	void reauth();

	boolean isConnected();

	void addConnectionListener(IMqttConnectionListener listener);
	void removeConnectionListener(IMqttConnectionListener listener);
}
