package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceFirmwareMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.matrix.IRGBMatrix;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.database.objects.*;
import lv.cecilutaka.cdtmanager2.server.device.bridge.Bridge;
import lv.cecilutaka.cdtmanager2.server.device.bridge.Relay;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.Floodlight;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.RGBFloodlight;
import lv.cecilutaka.cdtmanager2.server.device.matrix.RGBMatrix;
import lv.cecilutaka.cdtmanager2.server.json.DeviceFirmwareMessageFactory;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

/**
 * Connect counts as a firmware publish.
 */
@ConsumeMqttMessage(subscriptionId = 0)
public class MqttDeviceConnect extends MqttDeviceMessageConsumer
{
	private final ObjectMapper mapper = new ObjectMapper();

	public MqttDeviceConnect()
	{
		mapper.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Override
	public void consume(Server server, Mqtt5Publish publish, int mqttId) throws Exception
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length == 0) return;

		DeviceFirmwareMessageFactory factory = mapper.readValue(payload, DeviceFirmwareMessageFactory.class);
		IDeviceFirmwareMessage firmwareMessage = factory.build();

		String firmware = firmwareMessage.getFirmwareMessage();

		int fwIdBegin = 0;
		int fwIdEnd = firmware.indexOf(":", fwIdBegin);

		int fwBegin = firmware.indexOf(":", fwIdEnd) + 1;
		int fwEnd = firmware.length();

		try
		{
			int fwId = Integer.parseInt(firmware.substring(fwIdBegin, fwIdEnd));
			String fw = firmware.substring(fwBegin, fwEnd);

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

	private IDevice handleConnectPublish(Server server, int mqttId, int fwId, String fw)
	{
		DeviceType type = DeviceType.fromFirmwareId(fwId);

		server.getMqttDeviceTypeRegistry().register(mqttId, type);

		var devObj = new DeviceDAO(server.getDatabase());
		devObj.useHardwareIdAsKey(true);
		devObj.hardwareId = mqttId;
		devObj.firmwareType = fwId;
		devObj.firmware = fw;
		devObj.connected = true;
		devObj.uptime = 0;
		devObj.update();
		devObj.getId();

		switch (type)
		{
			case RELAY:
			{
				RegistryValue<IRelay> regValue = server.getRelayRegistry().get(devObj.id);
				IRelay relay;

				RelayDAO relayObj = devObj.asRelay();
				relayObj.update();

				if(regValue.isEmpty())
				{
					relay = new Relay(devObj.id);
					relay.setFirmwareInfo(new FirmwareInfo(type, fw));
					relay.setConnected(true);
					regValue.set(relay);
				}
				else
				{
					relay = regValue.get();
					relay.setId(devObj.id);
					relay.setConnected(true);
					relay.setFirmwareInfo(new FirmwareInfo(type, fw));
				}

				return relay;
			}
			case BRIDGE:
			{
				RegistryValue<IBridge> regValue = server.getBridgeRegistry().get(devObj.id);
				IBridge bridge;

				BridgeDAO bridgeObj = devObj.asBridge();
				bridgeObj.update();

				if(regValue.isEmpty())
				{
					bridge = new Bridge(devObj.id);
					bridge.setFirmwareInfo(new FirmwareInfo(type, fw));
					bridge.setConnected(true);
					regValue.set(bridge);
				}
				else
				{
					bridge = regValue.get();
					bridge.setId(devObj.id);
					bridge.setConnected(true);
					bridge.setFirmwareInfo(new FirmwareInfo(type, fw));
				}

				return bridge;
			}
			case MONO_FLOODLIGHT:
			{
				RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(devObj.id);
				IFloodlight floodlight;

				MonoFloodlightDAO fldlObj = devObj.asMonoFloodlight();
				fldlObj.flags = 0;
				fldlObj.flags = 0;
				fldlObj.update();

				if (regValue.isEmpty())
				{
					floodlight = new Floodlight(devObj.id);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					floodlight.setConnected(true);
					regValue.set(floodlight);
				}
				else
				{
					floodlight = regValue.get();
					floodlight.setId(devObj.id);
					floodlight.setConnected(true);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
				}

				return floodlight;
			}
			case RGB_FLOODLIGHT:
			{
				RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(devObj.id);
				IRGBFloodlight floodlight;

				RGBFloodlightDAO fldlObj = devObj.asRGBFloodlight();
				fldlObj.flags = 0;
				fldlObj.color = 0;
				fldlObj.fx = 0;
				fldlObj.update();

				if (regValue.isEmpty() || !(regValue.get() instanceof IRGBFloodlight))
				{
					floodlight = new RGBFloodlight(devObj.id);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					floodlight.setConnected(true);
					regValue.set(floodlight);
				}
				else
				{
					floodlight = (IRGBFloodlight) regValue.get();
					floodlight.setId(devObj.id);
					floodlight.setConnected(true);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
				}

				return floodlight;
			}
			case RGB_MATRIX:
			{
				RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(devObj.id);
				IRGBMatrix floodlight;

				RGBMatrixDAO matrixObj = devObj.asRGBMatrix();
				matrixObj.flags = 0;
				matrixObj.color = 0;
				matrixObj.fx = 0;
				matrixObj.update();

				if(regValue.isEmpty() || !(regValue.get() instanceof IRGBMatrix))
				{
					floodlight = new RGBMatrix(devObj.id);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
					floodlight.setConnected(true);
					regValue.set(floodlight);
				}
				else
				{
					floodlight = (IRGBMatrix) regValue.get();
					floodlight.setId(devObj.id);
					floodlight.setConnected(true);
					floodlight.setFirmwareInfo(new FirmwareInfo(type, fw));
				}

				return floodlight;
			}
			default: return null;

		}
	}
}
