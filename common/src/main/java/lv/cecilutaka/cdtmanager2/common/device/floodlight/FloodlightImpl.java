package lv.cecilutaka.cdtmanager2.common.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.device.IDeviceMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;

public class FloodlightImpl implements IFloodlight
{
	protected IDeviceMessage message;

	protected boolean turnedOn;
	protected IFirmwareInfo firmwareInfo;
	protected int uptime; // in seconds

	protected int id;

	public FloodlightImpl(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public boolean isTurnedOn()
	{
		return turnedOn;
	}

	@Override
	public IFirmwareInfo getFirmwareInfo()
	{
		return firmwareInfo;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public void setTurnedOn(boolean turnedOn)
	{
		this.turnedOn = turnedOn;
	}

	@Override
	public void setFirmwareInfo(IFirmwareInfo firmwareInfo)
	{
		this.firmwareInfo = firmwareInfo;
	}

	@Override
	public IDeviceMessage getLastMessage()
	{
		return message;
	}

	@Override
	public void setLastMessage(IDeviceMessage message)
	{
		this.message = message;
	}

	@Override
	public int getUptime()
	{
		return uptime;
	}

	@Override
	public void setUptime(int seconds)
	{
		this.uptime = seconds;
	}

	protected String _toStringPart0()
	{
		return "Floodlight";
	}

	protected String _toStringPart1()
	{
		return "(#" + id + ": " + firmwareInfo + ")";
	}

	@Override
	public String toString()
	{
		return _toStringPart0() + _toStringPart1();
	}
}
