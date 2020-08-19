package lv.cecilutaka.cdtmanager2.server.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.device.IBssidDevice;
import lv.cecilutaka.cdtmanager2.api.common.device.IMacAddress;
import lv.cecilutaka.cdtmanager2.api.common.device.NoDeviceBssidException;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.RGBFloodlightImpl;

public class RGBFloodlight extends RGBFloodlightImpl implements IBssidDevice
{
	protected IMacAddress bssid;

	public RGBFloodlight(int id)
	{
		super(id);
	}

	@Override
	public IMacAddress getBssid() throws NoDeviceBssidException
	{
		if(bssid == null) throw new NoDeviceBssidException();
		return bssid;
	}

	@Override
	public void setBssid(IMacAddress bssid)
	{
		this.bssid = bssid;
	}
}
