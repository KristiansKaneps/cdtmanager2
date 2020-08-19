package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.common.registry.SimpleRegistryImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import org.reflections.Reflections;

import java.util.Set;

public class SubscribedMessageHandler
{
	private final IRegistry<Integer, MqttLocalMessageConsumer> registry;

	private final MqttMessageConsumer mqttMessageConsumer;

	protected SubscribedMessageHandler(MqttMessageConsumer mqttMessageConsumer)
	{
		this.mqttMessageConsumer = mqttMessageConsumer;
		this.registry = new SimpleRegistryImpl<>();
	}

	private Server server()
	{
		return mqttMessageConsumer.server;
	}

	public void handle(Mqtt5Publish publish, int subscriptionId)
	{
		RegistryValue<MqttLocalMessageConsumer> regValue = registry.get(subscriptionId);

		if(regValue.isEmpty())
		{
			Log.w("MQTT", "Can't dispatch subscription ID #" + subscriptionId + " as there are no registered consumers for it.");
			return;
		}

		MqttLocalMessageConsumer consumer = regValue.get();

		Object[] parsedTopicLevels = consumer.parseTopicLevels(server(), publish.getTopic().getLevels());
		consumer.consume(server(), publish, parsedTopicLevels);
	}

	public void findAndRegisterConsumers()
	{
		Reflections reflections = new Reflections("lv.cecilutaka.cdtmanager2.server");
		Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ConsumeMqttMessage.class);
		for(Class<?> annotatedClass : annotatedClasses)
		{
			try
			{
				ConsumeMqttMessage annotation = annotatedClass.getAnnotation(ConsumeMqttMessage.class);
				int subscriptionId = annotation.subscriptionId();

				Log.d("MQTT", "Found @ConsumeMqttMessage consumer class (" + annotatedClass.getSimpleName() + ") with subscription ID = " + subscriptionId);

				Class<? extends MqttLocalMessageConsumer> castedClass = annotatedClass.asSubclass(MqttLocalMessageConsumer.class);

				MqttLocalMessageConsumer consumer = castedClass.getDeclaredConstructor().newInstance();
				registry.register(subscriptionId, consumer);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
