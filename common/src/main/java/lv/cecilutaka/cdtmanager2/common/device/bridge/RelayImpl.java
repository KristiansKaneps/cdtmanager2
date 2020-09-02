package lv.cecilutaka.cdtmanager2.common.device.bridge;

import lv.cecilutaka.cdtmanager2.api.common.device.json.IDeviceMessage;
import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;

public class RelayImpl implements IRelay
{
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
}
