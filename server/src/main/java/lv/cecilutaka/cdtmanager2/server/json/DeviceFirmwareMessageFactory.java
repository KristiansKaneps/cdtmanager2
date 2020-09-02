package lv.cecilutaka.cdtmanager2.server.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceFirmwareMessage;
import lv.cecilutaka.cdtmanager2.common.device.json.DeviceFirmwareMessage;

public class DeviceFirmwareMessageFactory
{
	private int msgType;
	private String firmwareMsg;

	@JsonCreator
	public DeviceFirmwareMessageFactory(
			@JsonProperty(value = "t", required = true) int msgType,
			@JsonProperty(value = "d", required = true) String firmwareMsg
	)
	{
		this.msgType = msgType;
		this.firmwareMsg = firmwareMsg;
	}

	public IDeviceFirmwareMessage build()
	{
		return new DeviceFirmwareMessage(firmwareMsg);
	}

	@JsonProperty("t")
	public void setMessageType(int msgType)
	{
		this.msgType = msgType;
	}

	@JsonProperty("d")
	public void setFirmwareMessage(String firmwareMsg)
	{
		this.firmwareMsg = firmwareMsg;
	}

	public int getMessageType()
	{
		return msgType;
	}

	public String getFirmwareMessage()
	{
		return firmwareMsg;
	}
}
