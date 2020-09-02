package lv.cecilutaka.cdtmanager2.api.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceMessage;

public interface IDevice
{
	IFirmwareInfo getFirmwareInfo();
	void setFirmwareInfo(IFirmwareInfo firmwareInfo);

	lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceMessage getLastMessage();
	void setLastMessage(IDeviceMessage message);

	/**
	 * @return uptime in seconds
	 */
	int getUptime();
	void setUptime(int seconds);

	long getLastUptimeReport();
}
