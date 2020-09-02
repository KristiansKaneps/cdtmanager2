package lv.cecilutaka.cdtmanager2.api.common.json;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceMessageType;

public interface IDeviceMessage
{
	String getMessage();
	DeviceMessageType getType();

	default DataType getDataType() { return DataType.MESSAGE; }
}
