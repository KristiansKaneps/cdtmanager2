package lv.cecilutaka.cdtmanager2.server.device.mqtt;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceUptimeMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.database.objects.BridgeDAO;
import lv.cecilutaka.cdtmanager2.server.database.objects.DeviceDAO;
import lv.cecilutaka.cdtmanager2.server.database.objects.RelayDAO;
import lv.cecilutaka.cdtmanager2.server.json.DeviceUptimeFactory;
import lv.cecilutaka.cdtmanager2.server.mqtt.ConsumeMqttMessage;

@ConsumeMqttMessage(subscriptionId = 1)
public class MqttDeviceUptime extends MqttDeviceMessageConsumer
{
	private final ObjectMapper mapper = new ObjectMapper();

	public MqttDeviceUptime()
	{
		mapper.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Override
	protected void consume(Server server, Mqtt5Publish publish, int mqttId) throws Exception
	{
		byte[] payload = publish.getPayloadAsBytes();

		DeviceUptimeFactory factory = mapper.readValue(payload, DeviceUptimeFactory.class);
		IDeviceUptimeMessage uptimeMsg = factory.build();

		int uptimeInSeconds = uptimeMsg.getUptime();

		RegistryValue<DeviceType> regType = server.getMqttDeviceTypeRegistry().get(mqttId);

		if(regType.isEmpty())
		{
			Log.d("MQTT Device", "Device #" + mqttId + " [uptime] = " + uptimeInSeconds + "s");
			return;
		}

		DeviceType type = regType.get();

		Log.d("MQTT Device", "Device (type=" + type + ") #" + mqttId + " [uptime] = " + uptimeInSeconds + "s");

		switch(type)
		{
			case RELAY:
			{
				var dao = new RelayDAO(server.getDatabase());
				dao.useHardwareIdAsKey(true);
				dao.hardwareId = mqttId;
				dao.getId();
				RegistryValue<IRelay> r = server.getRelayRegistry().get(dao.id);
				if (!r.isEmpty())
				{
					r.get().setUptime(uptimeInSeconds);
					r.get().setConnected(true);
				}
			} break;
			case BRIDGE:
			{
				var dao = new BridgeDAO(server.getDatabase());
				dao.useHardwareIdAsKey(true);
				dao.hardwareId = mqttId;
				dao.getId();
				RegistryValue<IBridge> r = server.getBridgeRegistry().get(dao.id);
				if(!r.isEmpty())
				{
					r.get().setUptime(uptimeInSeconds);
					r.get().setConnected(true);
				}
			}  break;
			case MONO_FLOODLIGHT:
			case RGB_FLOODLIGHT:
			case RGB_MATRIX:
			{
				var dao = new DeviceDAO(server.getDatabase());
				dao.useHardwareIdAsKey(true);
				dao.hardwareId = mqttId;
				dao.getId();
				RegistryValue<IFloodlight> r = server.getFloodlightRegistry().get(dao.id);
				if(!r.isEmpty())
				{
					r.get().setUptime(uptimeInSeconds);
					r.get().setConnected(true);
				}
			} break;
			default:
				return;
		}
	}
}