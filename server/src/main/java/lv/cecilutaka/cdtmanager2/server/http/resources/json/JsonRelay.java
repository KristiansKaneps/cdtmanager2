package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.common.device.bridge.RelayImpl;

public class JsonRelay extends JsonDevice
{
	public int id;

	public JsonRelay(RelayImpl device)
	{
		super(device);
		id = device.getId();
	}
}
