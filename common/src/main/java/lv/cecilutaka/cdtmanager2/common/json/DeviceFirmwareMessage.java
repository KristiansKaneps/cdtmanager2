package lv.cecilutaka.cdtmanager2.common.json;

import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceFirmwareMessage;

public class DeviceFirmwareMessage implements IDeviceFirmwareMessage
{
	private String firmwareMsg;

	public DeviceFirmwareMessage(String firmwareMsg)
	{
		this.firmwareMsg = firmwareMsg;
	}

	@Override
	public String getFirmwareMessage()
	{
		return firmwareMsg;
	}
}
