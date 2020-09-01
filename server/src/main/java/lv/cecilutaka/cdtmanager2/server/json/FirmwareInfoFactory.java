package lv.cecilutaka.cdtmanager2.server.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;

public class FirmwareInfoFactory
{
	private int firmwareId;
	private String firmware;

	@JsonCreator
	public FirmwareInfoFactory(
			@JsonProperty(value = "fwid", required = true) int firmwareId,
			@JsonProperty(value = "fw", required = true) String firmware)
	{
		this.firmwareId = firmwareId;
		this.firmware = firmware;
	}

	public IFirmwareInfo build()
	{
		return new FirmwareInfo(DeviceType.fromFirmwareId(firmwareId), firmware);
	}

	@JsonProperty("fwid")
	public void setFirmwareId(int firmwareId)
	{
		this.firmwareId = firmwareId;
	}

	@JsonProperty("fw")
	public void setFirmware(String firmware)
	{
		this.firmware = firmware;
	}

	public int getFirmwareId()
	{
		return firmwareId;
	}

	public String getFirmware()
	{
		return firmware;
	}
}
