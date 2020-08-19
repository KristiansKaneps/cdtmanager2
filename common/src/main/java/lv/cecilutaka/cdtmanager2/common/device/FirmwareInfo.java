package lv.cecilutaka.cdtmanager2.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;

public class FirmwareInfo implements IFirmwareInfo
{
	private final int firmwareId;
	private final String firmware;

	public FirmwareInfo(int firmwareId, String firmware)
	{
		this.firmwareId = firmwareId;
		this.firmware = firmware;
	}

	@Override
	public int getFirmwareId()
	{
		return firmwareId;
	}

	@Override
	public String getFirmware()
	{
		return firmware;
	}

	@Override
	public String toString()
	{
		return "FirmwareInfo(0x" + Integer.toHexString(firmwareId) + ": " + firmware + ")";
	}

	@Override
	public boolean equals(Object obj)
	{
		if(super.equals(obj)) return true;
		if(obj instanceof IFirmwareInfo)
		{
			IFirmwareInfo fw = (IFirmwareInfo) obj;
			return fw.getFirmwareId() == firmwareId && firmware != null && firmware.equals(fw.getFirmware());
		}
		return false;
	}
}
