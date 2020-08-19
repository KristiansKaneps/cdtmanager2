package lv.cecilutaka.cdtmanager2.api.server;

import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.api.server.config.INetworkRestConfig;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttClient;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.IMqttMessageConsumer;

public interface IServer
{
	INetworkMqttConfig getNetworkMqttConfig();
	INetworkRestConfig getNetworkRestConfig();

	IMqttClient getMqttClient();
	IMqttMessageConsumer getMqttMessageConsumer();

	IMqttFloodlightUtils getMqttFloodlightUtils();

	IRegistry<Integer, IFloodlight> getFloodlightRegistry();

	void start();
	void stop();

	boolean isRunning();
}
