package lv.cecilutaka.cdtmanager2.server.http.resources;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.FloodlightImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.JsonMonoFloodlight;
import lv.cecilutaka.cdtmanager2.server.registry.FloodlightRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonoFloodlightResource extends DeviceResource
{
	public MonoFloodlightResource(WebApplication app)
	{
		super(app);
	}

	@Override
	public List<? extends JsonMonoFloodlight> getDevices(Server server)
	{
		FloodlightRegistry registry = server.getFloodlightRegistry();
		Collection<RegistryValue<IFloodlight>> values = registry.getValueCollection();

		List<JsonMonoFloodlight> devices = new ArrayList<>();
		for(RegistryValue<IFloodlight> value : values)
			if(!value.isEmpty() && value.get().getFirmwareInfo().getFirmwareType() == DeviceType.MONO_FLOODLIGHT && value.get() instanceof FloodlightImpl)
				devices.add(new JsonMonoFloodlight((FloodlightImpl) value.get()));

		return devices;
	}
}
