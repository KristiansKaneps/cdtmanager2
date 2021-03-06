package lv.cecilutaka.cdtmanager2.server.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.cecilutaka.cdtmanager2.api.common.json.DataType;
import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceFirmwareMessage;
import lv.cecilutaka.cdtmanager2.common.json.DeviceFirmwareMessage;

public class DeviceFirmwareMessageFactory
{
	@Deprecated
	private int msgType = DataType.FIRMWARE_INFO.toId();
	private String firmwareMsg;

	@JsonCreator
	public DeviceFirmwareMessageFactory(
			@JsonProperty(value = "t", required = false) int msgType,
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

	@Deprecated
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

	@Deprecated
	public int getMessageType()
	{
		return msgType;
	}

	public String getFirmwareMessage()
	{
		return firmwareMsg;
	}
}
