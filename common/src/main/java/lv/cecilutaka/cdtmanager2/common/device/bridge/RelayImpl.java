package lv.cecilutaka.cdtmanager2.common.device.bridge;

import lv.cecilutaka.cdtmanager2.api.common.json.IDeviceMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;

public class RelayImpl implements IRelay
{
	protected boolean connected = false;

	protected long lastUptimeReport;

	protected IDeviceMessage message;

	protected IFirmwareInfo firmwareInfo;
	protected int uptime;

	protected int id;

	public RelayImpl(int id)
	{
		this.id = id;
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public IFirmwareInfo getFirmwareInfo()
	{
		return firmwareInfo;
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
		lastUptimeReport = System.currentTimeMillis();
		this.uptime = seconds;
	}

	@Override
	public long getLastUptimeReport()
	{
		return lastUptimeReport;
	}

	@Override
	public boolean isConnected()
	{
		return connected;
	}

	@Override
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}

	protected String _toStringPart0()
	{
		return "Relay";
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
