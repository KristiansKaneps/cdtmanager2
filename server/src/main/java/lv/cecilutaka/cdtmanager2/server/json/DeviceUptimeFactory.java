package lv.cecilutaka.cdtmanager2.server.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceUptimeMessage;
import lv.cecilutaka.cdtmanager2.common.device.json.DeviceUptimeMessage;

public class DeviceUptimeFactory
{
	private int msgType; // for uptime it's usually 44
	private int uptime;

	@JsonCreator
	public DeviceUptimeFactory(
			@JsonProperty(value = "t", required = true) int msgType,
			@JsonProperty(value = "d", required = true) int uptime
	)
	{
		this.msgType = msgType;
		this.uptime = uptime;
	}

	public IDeviceUptimeMessage build()
	{
		return new DeviceUptimeMessage(uptime);
	}

	@JsonProperty("t")
	public void setMessageType(int msgType)
	{
		this.msgType = msgType;
	}

	@JsonProperty("d")
	public void setUptime(int uptime)
	{
		this.uptime = uptime;
	}

	public int getMessageType()
	{
		return msgType;
	}

	public int getUptime()
	{
		return uptime;
	}
}
