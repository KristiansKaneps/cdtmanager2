package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.server.Server;

import java.util.List;

public interface MqttLocalMessageConsumer
{
	/**
	 * Handles incoming MQTT message.
	 * @param server - server instance
	 * @param publish - MQTT received publish
	 * @param parsedTopicLevels - topic levels that can be converted to any java object with {@code parseTopicLevels()}
	 */
	void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels);

	/**
	 * @return parsed topic levels that will be passed to {@code consume(.., parsedTopicLevels)}
	 */
	Object[] parseTopicLevels(Server server, List<String> topicLevels);
}
