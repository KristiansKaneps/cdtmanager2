package lv.cecilutaka.cdtmanager2.server;

import com.typesafe.config.Config;

import lv.cecilutaka.cdtmanager2.api.server.IServer;
import lv.cecilutaka.cdtmanager2.common.StartStopImpl;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.common.threading.Loop;
import lv.cecilutaka.cdtmanager2.server.config.ConfigLoader;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMySQLConfig;
import lv.cecilutaka.cdtmanager2.server.config.NetworkWebServiceConfig;
import lv.cecilutaka.cdtmanager2.server.database.Database;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClient;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttClientInitializer;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttGlobalUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttMessageConsumer;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttBridgeUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttRelayUtils;
import lv.cecilutaka.cdtmanager2.server.registry.*;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;

import java.sql.SQLException;
import java.util.Arrays;

public class Server extends StartStopImpl implements IServer
{
	private FloodlightRegistry floodlightRegistry;
	private RelayRegistry relayRegistry;
	private BridgeRegistry bridgeRegistry;
	private DeviceReadOnlyRegistry _deviceReadOnlyRegistry;

	private MqttDeviceTypeRegistry mqttDeviceTypeRegistry;

	private Loop mqttMessageConsumerLoop;
	private MqttMessageConsumer mqttMessageConsumer;
	private MqttClient mqttClient;

	private MqttGlobalUtils mqttUtils;

	private NetworkMqttConfig netMqttConfig;
	private NetworkWebServiceConfig netWebServiceConfig;
	private NetworkMySQLConfig netMySqlConfig;

	private final ConfigLoader configLoader;

	private WebApplication webApp;

	private Database database;

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
		_deviceReadOnlyRegistry = new DeviceReadOnlyRegistry();

		_deviceReadOnlyRegistry
				.addSubRegistry(floodlightRegistry)
				.addSubRegistry(relayRegistry)
				.addSubRegistry(bridgeRegistry);

		mqttUtils = new MqttGlobalUtils(
				new MqttRelayUtils(),
				new MqttBridgeUtils(),
				new MqttFloodlightUtils()
		);

		Log.i("Loading config...");
		Config networkConfig = configLoader.load("network.conf");

		netMqttConfig = configLoader.load(networkConfig.getConfig("mqtt"), NetworkMqttConfig.class);
		netWebServiceConfig = configLoader.load(networkConfig.getConfig("webservice"), NetworkWebServiceConfig.class);
		netMySqlConfig = configLoader.load(networkConfig.getConfig("mysql"), NetworkMySQLConfig.class);

		Log.i("Config loaded:");
		Log.i("MQTT Server Address = " + netMqttConfig.getHostname() + ":" + netMqttConfig.getPort());
		Log.i("MQTT User = " + netMqttConfig.getUser());
		Log.i("MQTT Use SSL = " + netMqttConfig.getSsl());
		if(netMqttConfig.getSsl()) Log.i("MQTT SSL Protocols = " + netMqttConfig.getSslProtocols().toString());
		Log.i("Web Service Response Type = " + netWebServiceConfig.getResponseType());
		Log.i("Web Service Address = "
		      + Arrays.toString(netWebServiceConfig.getHttpMethods().toArray(new String[0])) + " "
		      + netWebServiceConfig.getHostname() + ":" + netWebServiceConfig.getPort() + netWebServiceConfig.getResourceUri()
		);
		Log.i("MySQL Hostname = " + netMySqlConfig.getHostname());
		Log.i("MySQL Port = " + netMySqlConfig.getPort());

		try
		{
			database = new Database(this, netMySqlConfig);
			database.connect();
			database.createTables();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		mqttClient = new MqttClient(this);
		mqttClient.addConnectionListener(new MqttClientInitializer(this));

		mqttUtils.initialize();

		mqttMessageConsumerLoop = new Loop(
				mqttMessageConsumer = new MqttMessageConsumer(this, false),
				"CDTManager2 MQTT Message Consumer"
		);

		mqttMessageConsumerLoop.startAsync();
		mqttClient.connect();

		Log.i("Starting HTTP web server for REST.");
		java.util.logging.Logger.getLogger("io.netty").setLevel(java.util.logging.Level.SEVERE); // disable netty logger
		webApp = WebApplication.createWebApplication(this, netWebServiceConfig);
		webApp.start();

		Log.i("Started in " + (System.currentTimeMillis() - start) + " ms.");
	}

	@Override
	protected void onStop()
	{
		Log.i("Stopping...");

		webApp.stop();
		mqttClient.disconnect();
		mqttMessageConsumerLoop.stop();
		database.disconnect();

		Log.i("Stopped.");
	}

	@Override
	public NetworkMqttConfig getNetworkMqttConfig()
	{
		return netMqttConfig;
	}

	@Override
	public NetworkWebServiceConfig getNetworkWebServiceConfig()
	{
		return netWebServiceConfig;
	}

	@Override
	public NetworkMySQLConfig getMySQLConfig()
	{
		return netMySqlConfig;
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

	@Override
	public Database getDatabase()
	{
		return database;
	}

	public DeviceReadOnlyRegistry getDeviceReadOnlyRegistry()
	{
		return _deviceReadOnlyRegistry;
	}
}
