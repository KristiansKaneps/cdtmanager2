package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.common.device.matrix.RGBMatrixImpl;

public class JsonRGBMatrix extends JsonRGBFloodlight
{
	public JsonRGBMatrix(RGBMatrixImpl device)
	{
		super(device);
	}
}
