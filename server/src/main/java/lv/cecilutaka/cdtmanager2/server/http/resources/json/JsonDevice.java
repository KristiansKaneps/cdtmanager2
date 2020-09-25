package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;

public class JsonDevice
{
	public JsonFirmware firmware;
	public int uptime;

	public JsonDevice(IDevice device)
	{
		firmware = new JsonFirmware(device.getFirmwareInfo());
		uptime = device.getUptime();
	}
}
