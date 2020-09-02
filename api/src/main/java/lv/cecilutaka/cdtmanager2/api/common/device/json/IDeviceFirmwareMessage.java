package lv.cecilutaka.cdtmanager2.api.common.device.json;

public interface IDeviceFirmwareMessage
{
	String getFirmwareMessage();

	default DataType getDataType() { return DataType.FIRMWARE_INFO; }
}
