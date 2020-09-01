package lv.cecilutaka.cdtmanager2.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;

public class FirmwareInfo implements IFirmwareInfo
{
	private final DeviceType firmwareType;
	private final String firmware;

	public FirmwareInfo(DeviceType firmwareType, String firmware)
	{
		this.firmwareType = firmwareType;
		this.firmware = firmware;
	}

	@Override
	public DeviceType getFirmwareType()
	{
		return firmwareType;
	}

	@Override
	public String getFirmware()
	{
		return firmware;
	}

	@Override
	public String toString()
	{
		return "FirmwareInfo(" + firmwareType.name() + ": " + firmware + ")";
	}

	@Override
	public boolean equals(Object obj)
	{
		if(super.equals(obj)) return true;
		if(obj instanceof IFirmwareInfo)
		{
			IFirmwareInfo fw = (IFirmwareInfo) obj;
			return fw.getFirmwareType() == firmwareType && firmware != null && firmware.equals(fw.getFirmware());
		}
		return false;
	}
}
