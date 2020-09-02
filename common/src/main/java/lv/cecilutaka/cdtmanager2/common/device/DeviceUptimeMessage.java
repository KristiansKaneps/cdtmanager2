package lv.cecilutaka.cdtmanager2.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.IDeviceUptimeMessage;

public class DeviceUptimeMessage implements IDeviceUptimeMessage
{
	private int uptime;

	public DeviceUptimeMessage(int uptime)
	{
		this.uptime = uptime;
	}

	@Override
	public int getUptime()
	{
		return uptime;
	}
}
