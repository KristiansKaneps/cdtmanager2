package lv.cecilutaka.cdtmanager2.server.http.resources;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.matrix.RGBMatrixImpl;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.JsonRGBMatrix;
import lv.cecilutaka.cdtmanager2.server.registry.FloodlightRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RGBMatrixResource extends RGBFloodlightResource
{
	public RGBMatrixResource(WebApplication app)
	{
		super(app);
	}

	@Override
	public List<? extends JsonRGBMatrix> getDevices(Server server)
	{
		FloodlightRegistry registry = server.getFloodlightRegistry();
		Collection<RegistryValue<IFloodlight>> values = registry.getValueCollection();

		List<JsonRGBMatrix> devices = new ArrayList<>();
		for(RegistryValue<IFloodlight> value : values)
			if(!value.isEmpty() && value.get().getFirmwareInfo().getFirmwareType() == DeviceType.RGB_MATRIX && value.get() instanceof RGBMatrixImpl)
				devices.add(new JsonRGBMatrix((RGBMatrixImpl) value.get()));

		return devices;
	}
}
