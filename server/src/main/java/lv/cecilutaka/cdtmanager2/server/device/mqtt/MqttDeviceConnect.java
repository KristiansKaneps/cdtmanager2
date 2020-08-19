package lv.cecilutaka.cdtmanager2.server.device.mqtt;


import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.matrix.IRGBMatrix;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.api.server.mqtt.MqttIdException;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.Floodlight;
import lv.cecilutaka.cdtmanager2.server.device.floodlight.RGBFloodlight;
import lv.cecilutaka.cdtmanager2.server.device.matrix.RGBMatrix;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;
import lv.cecilutaka.cdtmanager2.server.mqtt.MqttSimpleLocalMessageConsumer;

import java.nio.charset.StandardCharsets;

@ConsumeMqttMessage(subscriptionId = 0)
public class MqttDeviceConnect implements MqttSimpleLocalMessageConsumer
{
	@Override
	public void consume(Server server, Mqtt5Publish publish)
	{
		byte[] payload = publish.getPayloadAsBytes();
		if(payload.length == 0) return;

		boolean alreadyConnected = publish.getTopic().getLevels().size() == 3 && "ac".equals(publish.getTopic().getLevels().get(2));

		String content = new String(payload, StandardCharsets.UTF_8);

		int hexBegin = content.indexOf("0x") + 2;
		int hexEnd = content.indexOf(" ", hexBegin);

		int fwIdBegin = content.indexOf("0x", hexEnd) + 2;
		int fwIdEnd = content.indexOf(" ", fwIdBegin);

		int fwBegin = content.indexOf(": ", fwIdEnd) + 2;
		int fwEnd = content.length();

		try
		{
			String mqttId = content.substring(hexBegin, hexEnd);
			int fwId = Integer.parseInt(content.substring(fwIdBegin, fwIdEnd));
			String fw = content.substring(fwBegin, fwEnd);

			IDevice device = handleConnectPublish(server, mqttId, fwId, fw);
			if (device != null) Log.i("Device", (alreadyConnected ? "A device was already connected to broker: " : "A new device connected: ") + device.toString());
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

		try
		{
			switch (type)
			{
				case MONO_FLOODLIGHT:
				{
					int floodlightId = server.getMqttFloodlightUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IFloodlight floodlight;

					if (regValue.isEmpty())
					{
						floodlight = new Floodlight(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
					}

					return floodlight;
				}
				case RGB_FLOODLIGHT:
				{
					int floodlightId = server.getMqttFloodlightUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IRGBFloodlight floodlight;

					if (regValue.isEmpty() || !(regValue.get() instanceof IRGBFloodlight))
					{
						floodlight = new RGBFloodlight(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = (IRGBFloodlight) regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
					}

					return floodlight;
				}
				case RGB_MATRIX:
				{
					int floodlightId = server.getMqttFloodlightUtils().toFloodlightId(mqttId);
					RegistryValue<IFloodlight> regValue = server.getFloodlightRegistry().get(floodlightId);
					IRGBMatrix floodlight;

					if(regValue.isEmpty() || !(regValue.get() instanceof IRGBMatrix))
					{
						floodlight = new RGBMatrix(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
						regValue.set(floodlight);
					}
					else
					{
						floodlight = (IRGBMatrix) regValue.get();
						floodlight.setId(floodlightId);
						floodlight.setFirmwareInfo(new FirmwareInfo(fwId, fw));
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
