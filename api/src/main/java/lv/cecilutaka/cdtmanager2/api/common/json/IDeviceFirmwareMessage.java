package lv.cecilutaka.cdtmanager2.api.common.json;

public interface IDeviceFirmwareMessage
{
	String getFirmwareMessage();

	default DataType getDataType() { return DataType.FIRMWARE_INFO; }
}
