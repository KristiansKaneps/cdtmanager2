package lv.cecilutaka.cdtmanager2.server.mqtt;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttMessageConsumer;
import lv.cecilutaka.cdtmanager2.common.threading.Loop;
import lv.cecilutaka.cdtmanager2.common.threading.LoopRunnable;
import lv.cecilutaka.cdtmanager2.server.Server;

public class MqttMessageConsumer implements IMqttMessageConsumer, LoopRunnable
{
	private volatile boolean running = false;

	private SubscribedMessageHandler subscribedMessageHandler;
	private UnsolicitedMessageHandler unsolicitedMessageHandler;

	protected final Server server;
	private final boolean enableLoop;

	public MqttMessageConsumer(Server server, boolean enableLoop)
	{
		this.server = server;
		this.enableLoop = enableLoop;
		subscribedMessageHandler = new SubscribedMessageHandler(this);
		unsolicitedMessageHandler = new UnsolicitedMessageHandler(this);
	}

	@Override
	public void run()
	{
		running = true;

		MqttClient client = server.getMqttClient();
		Mqtt5AsyncClient mqtt = client.mqtt();

		subscribedMessageHandler.findAndRegisterConsumers();

		while(enableLoop && running)
		{
			if(client.isConnected())
			{
				fetchUnsolicitedMessages(mqtt);

				Loop.sleep(50);
				continue;
			}

			Loop.sleep(500);
		}
	}

	protected void fetchUnsolicitedMessages(Mqtt5AsyncClient mqtt)
	{
		mqtt.publishes(MqttGlobalPublishFilter.UNSOLICITED, publish -> {
			publish.acknowledge();
			unsolicitedMessageHandler.handle(publish);
		}, true);
	}

	/**
	 * Gets called by callbacks created when subscribing to topic
	 */
	protected void handleSubscribedMessage(Mqtt5Publish publish, int subscriptionId)
	{
		subscribedMessageHandler.handle(publish, subscriptionId);
	}

	@Override
	public void stop()
	{
		running = false;
	}

	@Override
	public boolean isRunning()
	{
		return running;
	}
}
