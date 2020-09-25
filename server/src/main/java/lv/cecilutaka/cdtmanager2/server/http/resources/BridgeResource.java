package lv.cecilutaka.cdtmanager2.server.http.resources;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.bridge.BridgeImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.JsonBridge;
import lv.cecilutaka.cdtmanager2.server.registry.BridgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BridgeResource extends RelayResource
{
	public BridgeResource(WebApplication app)
	{
		super(app);
	}

	@Override
	public List<? extends JsonBridge> getDevices(Server server)
	{
		BridgeRegistry registry = server.getBridgeRegistry();
		Collection<RegistryValue<IBridge>> values = registry.getValueCollection();

		List<JsonBridge> devices = new ArrayList<>();
		for(RegistryValue<IBridge> value : values)
			if(!value.isEmpty() && value.get() instanceof BridgeImpl) devices.add(new JsonBridge((BridgeImpl) value.get()));

		return devices;
	}
}
