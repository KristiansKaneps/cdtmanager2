package lv.cecilutaka.cdtmanager2.api.common.device;

public interface IBssidDevice extends IDevice
{
	IMacAddress getBssid() throws NoDeviceBssidException;
	void setBssid(IMacAddress bssid);
}