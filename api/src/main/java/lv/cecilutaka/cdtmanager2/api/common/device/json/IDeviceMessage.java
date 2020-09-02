package lv.cecilutaka.cdtmanager2.api.common.device.json;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceMessageType;

public interface IDeviceMessage
{
	String getMessage();
	DeviceMessageType getType();
}
