package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.common.device.bridge.BridgeImpl;

public class JsonBridge extends JsonRelay
{
	public JsonBridge(BridgeImpl device)
	{
		super(device);
	}
}
