package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.common.device.floodlight.RGBFloodlightImpl;

public class JsonRGBFloodlight extends JsonMonoFloodlight
{
	public int color;
	public int fx;

	public JsonRGBFloodlight(RGBFloodlightImpl device)
	{
		super(device);
		color = device.getColor().getRGBA();
		fx = 0;
	}
}
