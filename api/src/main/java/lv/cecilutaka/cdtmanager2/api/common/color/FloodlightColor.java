package lv.cecilutaka.cdtmanager2.api.common.color;

/**
 * Alpha channel is floodlight's brightness
 */
public class FloodlightColor extends Color
{
	public FloodlightColor()
	{
		super();
	}

	public FloodlightColor(int rgba)
	{
		super(rgba);
	}

	public FloodlightColor(int r, int g, int b)
	{
		super(r, g, b);
	}

	public FloodlightColor(int r, int g, int b, int a)
	{
		super(r, g, b, a);
	}
}
