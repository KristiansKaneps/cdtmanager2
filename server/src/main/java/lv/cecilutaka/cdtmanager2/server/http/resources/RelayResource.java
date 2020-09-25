package lv.cecilutaka.cdtmanager2.server.http.resources;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.bridge.RelayImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.JsonRelay;
import lv.cecilutaka.cdtmanager2.server.registry.RelayRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RelayResource extends DeviceResource
{
	public RelayResource(WebApplication app)
	{
		super(app);
	}

	@Override
	public List<? extends JsonRelay> getDevices(Server server)
	{
		RelayRegistry registry = server.getRelayRegistry();
		Collection<RegistryValue<IRelay>> values = registry.getValueCollection();

		List<JsonRelay> devices = new ArrayList<>();
		for(RegistryValue<IRelay> value : values)
			if(!value.isEmpty() && value.get() instanceof RelayImpl) devices.add(new JsonRelay((RelayImpl) value.get()));

		return devices;
	}
}
