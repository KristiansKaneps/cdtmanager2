package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.matrix.IRGBMatrix;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.device.bridge.Bridge;
import lv.cecilutaka.cdtmanager2.server.device.bridge.Relay;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.Floodlight;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.RGBFloodlight;
import lv.cecilutaka.cdtmanager2.server.device.matrix.RGBMatrix;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * Connect counts as a firmware publish
 */
@ConsumeMqttMessage(subscriptionId = 0)
public class MqttDeviceConnect extends MqttDeviceMessageConsumer
{
	@Override
	public void consume(Server server, Mqtt5Publish publish, String mqttId)
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length == 0) return;

		String content = new String(payload, StandardCharsets.UTF_8);

		int fwIdBegin = 0;
		int fwIdEnd = content.indexOf(":", fwIdBegin);

		int fwBegin = content.indexOf(":", fwIdEnd) + 1;
		int fwEnd = content.length();

		try
		{
			int fwId = Integer.parseInt(content.substring(fwIdBegin, fwIdEnd));
			String fw = content.substring(fwBegin, fwEnd);

			IDevice device = handleConnectPublish(server, mqttId, fwId, fw);
			if (device != null) Log.i("Device", "A device connected: " + device.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/*if(!alreadyConnected)
		{
			// we need to update local mesh information on a new connection from single node (because that node can have more nodes)
			server.getMqttClient().broadcastConnected();
		}*/
	}

	private IDevice handleConnectPublish(Server server, String mqttId, int fwId, String fw)
	{
		DeviceType type = DeviceType.fromFirmwareId(fwId);

		server.getMqttDeviceTypeRegistry().register(mqttId, type);

		try
		{
			switch (type)
			{
				case RELAY:
				{
					int relayId = server.getMqttUtils().toRelayId(mqttId);
					RegistryValue<IRelay> regValue = server.getRelayRegistry().get(relayId);
					IRelay relay;

					if(regValue.isEmpty())
					{
						relay = new Relay(relayId);
						relay.setFirmwareInfo(new FirmwareInfo(type, fw));
						regValue.set(relay);
					}
					else
					{
						relay = regValue.get();
						relay.setId(relayId);
						relay.setFirmwareInfo(new FirmwareInfo(type, fw));
					}

					return relay;
				}
				case BRIDGE:
				{
					int bridgeId = server.getMqttUtils().toBridgeId(mqttId);
					RegistryValue<IBridge> regValue = server.getBridgeRegistry().get(bridgeId);
					IBridge bridge;

					if(regValue.isEmpty())
					{
						bridge = new Bridge(bridgeId);
						bridge.setFirmwareInfo(new FirmwareInfo(type, fw));
						regValue.set(bridge);
					}
					else
					{
						bridge = regValue.get();
						bridge.setId(bridgeId);
						bridge.setFirmwareInfo(new FirmwareInfo(type, fw));
					}

					return bridge;
				}
				case MONO_FLOODLIGHT:
				{
					int floodlightId = server.getMqttUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IFloodlight floodlight;

					if (regValue.isEmpty())
					{
						floodlight = new Floodlight(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					}

					return floodlight;
				}
				case RGB_FLOODLIGHT:
				{
					int floodlightId = server.getMqttUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IRGBFloodlight floodlight;

					if (regValue.isEmpty() || !(regValue.get() instanceof IRGBFloodlight))
					{
						floodlight = new RGBFloodlight(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = (IRGBFloodlight) regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					}

					return floodlight;
				}
				case RGB_MATRIX:
				{
					int floodlightId = server.getMqttUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IRGBMatrix floodlight;

					if(regValue.isEmpty() || !(regValue.get() instanceof IRGBMatrix))
					{
						floodlight = new RGBMatrix(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = (IRGBMatrix) regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					}

					return floodlight;
				}
				default: return null;

			}
		}
		catch(MqttIdException e)
		{
			Log.w("MQTT", "A device sent an invalid MQTT ID: '" + mqttId + "'");
		}

		return null;
	}
}
