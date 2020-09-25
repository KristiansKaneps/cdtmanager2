package lv.cecilutaka.cdtmanager2.server.http.resources;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.RGBFloodlightImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.JsonRGBFloodlight;
import lv.cecilutaka.cdtmanager2.server.registry.FloodlightRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RGBFloodlightResource extends MonoFloodlightResource
{
	public RGBFloodlightResource(WebApplication app)
	{
		super(app);
	}

	@Override
	public List<? extends JsonRGBFloodlight> getDevices(Server server)
	{
		FloodlightRegistry registry = server.getFloodlightRegistry();
		Collection<RegistryValue<IFloodlight>> values = registry.getValueCollection();

		List<JsonRGBFloodlight> devices = new ArrayList<>();
		for(RegistryValue<IFloodlight> value : values)
			if(!value.isEmpty() && value.get().getFirmwareInfo().getFirmwareType() == DeviceType.RGB_FLOODLIGHT && value.get() instanceof RGBFloodlightImpl)
				devices.add(new JsonRGBFloodlight((RGBFloodlightImpl) value.get()));

		return devices;
	}
}
