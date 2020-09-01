package lv.cecilutaka.cdtmanager2.server;

import com.typesafe.config.Config;

import lv.cecilutaka.cdtmanager2.api.server.IServer;
import lv.cecilutaka.cdtmanager2.common.StartStopImpl;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.common.threading.Loop;
import lv.cecilutaka.cdtmanager2.server.config.ConfigLoader;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.server.config.NetworkRestConfig;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClient;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClientInitializer;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttGlobalUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttMessageConsumer;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttBridgeUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttRelayUtils;
import lv.cecilutaka.cdtmanager2.server.registry.BridgeRegistry;
import lv.cecilutaka.cdtmanager2.server.registry.FloodlightRegistry;
import lv.cecilutaka.cdtmanager2.server.registry.MqttDeviceTypeRegistry;
import lv.cecilutaka.cdtmanager2.server.registry.RelayRegistry;

public class Server extends StartStopImpl implements IServer
{
	private FloodlightRegistry floodlightRegistry;
	private RelayRegistry relayRegistry;
	private BridgeRegistry bridgeRegistry;

	private MqttDeviceTypeRegistry mqttDeviceTypeRegistry;

	private Loop mqttMessageConsumerLoop;
	private MqttMessageConsumer mqttMessageConsumer;
	private MqttClient mqttClient;

	private MqttGlobalUtils mqttUtils;

	private NetworkMqttConfig netMqttConfig;
	private NetworkRestConfig netRestConfig;

	private final ConfigLoader configLoader;

	public Server()
	{
		this.configLoader = new ConfigLoader();
	}

	@Override
	protected void onStart()
	{
		Log.i("Starting...");
		long start = System.currentTimeMillis();

		mqttDeviceTypeRegistry = new MqttDeviceTypeRegistry();

		floodlightRegistry = new FloodlightRegistry();
		relayRegistry = new RelayRegistry();
		bridgeRegistry = new BridgeRegistry();

		mqttUtils = new MqttGlobalUtils(
				new MqttRelayUtils(),
				new MqttBridgeUtils(),
				new MqttFloodlightUtils()
		);

		Log.i("Loading config...");
		Config networkConfig = configLoader.load("network.conf");

		netMqttConfig = configLoader.load(networkConfig.getConfig("mqtt"), NetworkMqttConfig.class);
		netRestConfig = configLoader.load(networkConfig.getConfig("rest"), NetworkRestConfig.class);
		Log.i("Config loaded:");
		Log.i("MQTT Server Address = " + netMqttConfig.getIp() + ":" + netMqttConfig.getPort());
		Log.i("MQTT User = " + netMqttConfig.getUser());
		Log.i("MQTT Use SSL = " + netMqttConfig.getSsl());
		if(netMqttConfig.getSsl()) Log.i("MQTT SSL Protocols = " + netMqttConfig.getSslProtocols().toString());
		Log.i("REST Listening on = " + netRestConfig.getIp() + ":" + netRestConfig.getPort());

		mqttClient = new MqttClient(this);
		mqttClient.addConnectionListener(new MqttClientInitializer(this));

		mqttUtils.initialize();

		mqttMessageConsumerLoop = new Loop(mqttMessageConsumer = new MqttMessageConsumer(this, false), "CDTManager2 MQTT Message Consumer");

		mqttMessageConsumerLoop.startAsync();
		mqttClient.connect();

		Log.i("Started in " + (System.currentTimeMillis() - start) + " ms.");
	}

	@Override
	protected void onStop()
	{
		Log.i("Stopping...");

		mqttClient.disconnect();
		mqttMessageConsumerLoop.stop();

		Log.i("Stopped.");
	}

	@Override
	public NetworkMqttConfig getNetworkMqttConfig()
	{
		return netMqttConfig;
	}

	@Override
	public NetworkRestConfig getNetworkRestConfig()
	{
		return netRestConfig;
	}

	@Override
	public MqttClient getMqttClient()
	{
		return mqttClient;
	}

	@Override
	public MqttMessageConsumer getMqttMessageConsumer()
	{
		return mqttMessageConsumer;
	}

	@Override
	public MqttGlobalUtils getMqttUtils()
	{
		return mqttUtils;
	}

	@Override
	public FloodlightRegistry getFloodlightRegistry()
	{
		return floodlightRegistry;
	}

	@Override
	public RelayRegistry getRelayRegistry()
	{
		return relayRegistry;
	}

	@Override
	public BridgeRegistry getBridgeRegistry()
	{
		return bridgeRegistry;
	}

	@Override
	public MqttDeviceTypeRegistry getMqttDeviceTypeRegistry()
	{
		return mqttDeviceTypeRegistry;
	}
}
