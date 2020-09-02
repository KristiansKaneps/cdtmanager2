package lv.cecilutaka.cdtmanager2.common.device.json;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceMessageType;
import lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceMessage;

public class DeviceMessage implements IDeviceMessage
{
	private String message;
	private DeviceMessageType type;

	public DeviceMessage(String message, DeviceMessageType type)
	{
		this.message = message;
		this.type = type;
	}

	@Override
	public String getMessage()
	{
		return message;
	}

	@Override
	public DeviceMessageType getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return '[' + type.name() + "] " + message;
	}
}
