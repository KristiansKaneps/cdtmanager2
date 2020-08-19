package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.server.Server;

import java.util.List;

public interface MqttSimpleLocalMessageConsumer extends MqttLocalMessageConsumer
{
	/**
	 * Handles incoming MQTT message.
	 * @param server - server instance
	 * @param publish - MQTT received publish
	 */
	void consume(Server server, Mqtt5Publish publish);

	@Override
	default void consume(Server server, Mqtt5Publish publish, Object[] parsedTopicLevels) { this.consume(server, publish); }

	@Override
	default Object[] parseTopicLevels(Server server, List<String> topicLevels) { return null; }
}
