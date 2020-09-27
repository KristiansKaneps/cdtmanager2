package lv.cecilutaka.cdtmanager2.api.common.device;

import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceMessage;

public interface IDevice
{
	/**
	 * @return local ID
	 */
	int getId();
	void setId(int id);

	IFirmwareInfo getFirmwareInfo();
	void setFirmwareInfo(IFirmwareInfo firmwareInfo);

	IDeviceMessage getLastMessage();
	void setLastMessage(IDeviceMessage message);

	/**
	 * @return uptime in seconds
	 */
	int getUptime();
	void setUptime(int seconds);

	long getLastUptimeReport();
}
