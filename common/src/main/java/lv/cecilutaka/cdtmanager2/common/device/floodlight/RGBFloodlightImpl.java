package lv.cecilutaka.cdtmanager2.common.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.color.FloodlightColor;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;

public class RGBFloodlightImpl extends FloodlightImpl implements IRGBFloodlight
{
	protected final FloodlightColor color = new FloodlightColor();
	protected byte fx = 0;

	public RGBFloodlightImpl(int id)
	{
		super(id);
	}

	@Override
	public FloodlightColor getColor()
	{
		return color;
	}

	@Override
	public byte getFX()
	{
		return fx;
	}

	@Override
	public void setFX(byte fx)
	{
		this.fx = fx;
	}

	@Override
	protected String _toStringPart0()
	{
		return "RGBFloodlight";
	}
}
