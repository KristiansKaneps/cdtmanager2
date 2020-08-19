package lv.cecilutaka.cdtmanager2.server.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.device.IBssidDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.IMacAddress;
import lv.cecilutaka.cdtmanager2.api.common.device.NoDeviceBssidException;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.FloodlightImpl;

public class Floodlight extends FloodlightImpl implements IBssidDevice
{
	protected IMacAddress bssid;

	public Floodlight(int id)
	{
		super(id);
	}

	public IMacAddress getBssid() throws NoDeviceBssidException
	{
		if(bssid == null) throw new NoDeviceBssidException();
		return bssid;
	}

	public void setBssid(IMacAddress bssid)
	{
		this.bssid = bssid;
	}
}
