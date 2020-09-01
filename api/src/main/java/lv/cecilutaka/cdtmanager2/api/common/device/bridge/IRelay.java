package lv.cecilutaka.cdtmanager2.api.common.device.bridge;

import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;

public interface IRelay extends IDevice
{
	int getId();
	void setId(int id);
}
