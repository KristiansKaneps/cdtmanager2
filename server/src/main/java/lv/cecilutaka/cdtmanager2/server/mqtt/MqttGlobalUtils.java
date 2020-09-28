package lv.cecilutaka.cdtmanager2.server.mqtt;

import lv.cecilutaka.cdtmanager2.api.server.mqtt.*;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttBridgeUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttFloodlightUtils;
import lv.cecilutaka.cdtmanager2.server.mqtt.utils.MqttRelayUtils;

public class MqttGlobalUtils implements IMqttGlobalUtils
{
	protected MqttRelayUtils relayUtils;
	protected MqttBridgeUtils bridgeUtils;
	protected MqttFloodlightUtils floodlightUtils;

	public MqttGlobalUtils(MqttRelayUtils relayUtils, MqttBridgeUtils bridgeUtils, MqttFloodlightUtils floodlightUtils)
	{
		this.relayUtils = relayUtils;
		this.bridgeUtils = bridgeUtils;
		this.floodlightUtils = floodlightUtils;
	}

	@Override
	public void initialize()
	{
		relayUtils.initialize();
		bridgeUtils.initialize();
		floodlightUtils.initialize();
	}

	@Override
	public IMqttRelayUtils getRelayUtils()
	{
		return relayUtils;
	}

	@Override
	public IMqttBridgeUtils getBridgeUtils()
	{
		return bridgeUtils;
	}

	@Override
	public IMqttFloodlightUtils getFloodlightUtils()
	{
		return floodlightUtils;
	}

	@Override
	public int toRelayId(int mqttRelayId) throws MqttIdException
	{
		return relayUtils.toRelayId(mqttRelayId);
	}

	@Override
	public int toMqttRelayId(int relayId) throws MqttIdException
	{
		return relayUtils.toMqttRelayId(relayId);
	}

	@Override
	public int toBridgeId(int mqttBridgeId) throws MqttIdException
	{
		return bridgeUtils.toBridgeId(mqttBridgeId);
	}

	@Override
	public int toMqttBridgeId(int bridgeId) throws MqttIdException
	{
		return bridgeUtils.toMqttBridgeId(bridgeId);
	}

	@Override
	public int toFloodlightId(int mqttFloodlightId) throws MqttIdException
	{
		return floodlightUtils.toFloodlightId(mqttFloodlightId);
	}

	@Override
	public int toMqttFloodlightId(int floodlightId) throws MqttIdException
	{
		return floodlightUtils.toMqttFloodlightId(floodlightId);
	}
}
