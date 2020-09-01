package lv.cecilutaka.cdtmanager2.server.device.bridge;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.common.device.bridge.BridgeImpl;

public class Bridge extends BridgeImpl implements IBridge
{
	public Bridge(int id)
	{
		super(id);
	}
}
