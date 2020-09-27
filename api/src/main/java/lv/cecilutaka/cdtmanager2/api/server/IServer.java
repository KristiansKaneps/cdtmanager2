package lv.cecilutaka.cdtmanager2.api.server;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMySQLConfig;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkWebServiceConfig;
import lv.cecilutaka.cdtmanager2.api.server.database.IDatabase;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.*;

public interface IServer
{
	INetworkMqttConfig getNetworkMqttConfig();
	INetworkWebServiceConfig getNetworkWebServiceConfig();
	INetworkMySQLConfig getMySQLConfig();

	IMqttClient getMqttClient();
	IMqttMessageConsumer getMqttMessageConsumer();

	IMqttGlobalUtils getMqttUtils();

	IRegistry<Integer, IFloodlight> getFloodlightRegistry();
	IRegistry<Integer, IRelay> getRelayRegistry();
	IRegistry<Integer, IBridge> getBridgeRegistry();

	IRegistry<String, DeviceType> getMqttDeviceTypeRegistry();

	IDatabase getDatabase();

	void start();
	void stop();

	boolean isRunning();
}
