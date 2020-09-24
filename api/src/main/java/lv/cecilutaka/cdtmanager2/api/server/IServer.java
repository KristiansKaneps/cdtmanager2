package lv.cecilutaka.cdtmanager2.api.server;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkHttpConfig;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.*;

public interface IServer
{
	INetworkMqttConfig getNetworkMqttConfig();
	INetworkHttpConfig getNetworkHttpConfig();

	IMqttClient getMqttClient();
	IMqttMessageConsumer getMqttMessageConsumer();

	IMqttGlobalUtils getMqttUtils();

	IRegistry<Integer, IFloodlight> getFloodlightRegistry();
	IRegistry<Integer, IRelay> getRelayRegistry();
	IRegistry<Integer, IBridge> getBridgeRegistry();

	IRegistry<String, DeviceType> getMqttDeviceTypeRegistry();

	void start();
	void stop();

	boolean isRunning();
}
