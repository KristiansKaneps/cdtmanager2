package lv.cecilutaka.cdtmanager2.server.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceMessageType;
import lv.cecilutaka.cdtmanager2.api.common.json.DataType;
import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceMessage;
import lv.cecilutaka.cdtmanager2.common.json.DeviceMessage;

public class DeviceMessageFactory
{
	private int messageTypeId = DataType.MESSAGE.toId();
	private String message;

	@JsonCreator
	public DeviceMessageFactory(
			@JsonProperty(value = "t", required = false) int messageTypeId,
			@JsonProperty(value = "m", required = true) String message)
	{
		this.messageTypeId = messageTypeId;
		this.message = message;
	}

	public IDeviceMessage build()
	{
		return new DeviceMessage(message, DeviceMessageType.fromTypeId(messageTypeId));
	}

	@JsonProperty("t")
	public void setMessageTypeId(int messageTypeId)
	{
		this.messageTypeId = messageTypeId;
	}

	public void setMessageType(DeviceMessageType messageType)
	{
		this.messageTypeId = messageType.getTypeId();
	}

	@JsonProperty("m")
	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getMessageTypeId()
	{
		return messageTypeId;
	}

	public DeviceMessageType getMessageType()
	{
		return DeviceMessageType.fromTypeId(messageTypeId);
	}

	public String getMessage()
	{
		return message;
	}
}
