package lv.cecilutaka.cdtmanager2.api.server;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkRestConfig;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.*;

public interface IServer
{
	INetworkMqttConfig getNetworkMqttConfig();
	INetworkRestConfig getNetworkRestConfig();

	IMqttClient getMqttClient();
	IMqttMessageConsumer getMqttMessageConsumer();

	IMqttFloodlightUtils getMqttFloodlightUtils();
	IMqttRelayUtils getMqttRelayUtils();
	IMqttBridgeUtils getMqttBridgeUtils();

	IRegistry<Integer, IFloodlight> getFloodlightRegistry();
	IRegistry<Integer, IRelay> getRelayRegistry();
	IRegistry<Integer, IBridge> getBridgeRegistry();

	void start();
	void stop();

	boolean isRunning();
}
