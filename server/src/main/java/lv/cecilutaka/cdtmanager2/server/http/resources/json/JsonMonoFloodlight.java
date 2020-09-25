package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.common.device.floodlight.FloodlightImpl;

public class JsonMonoFloodlight extends JsonDevice
{
	public int id;
	public byte flags;

	public JsonMonoFloodlight(FloodlightImpl device)
	{
		super(device);
		byte flags = 0x00;
		flags |= device.isTurnedOn() ? 0x01 : 0x00;

		id = device.getId();
		this.flags = flags;
	}
}
