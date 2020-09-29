package lv.cecilutaka.cdtmanager2.api.common.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.color.FloodlightColor;

public interface IRGBFloodlight extends IFloodlight
{
	/**
	 * @return floodlight's color object (which is final)
	 */
	FloodlightColor getColor();

	/**
	 * @return FX
	 */
	byte getFX();

	default void setColor(FloodlightColor color) { getColor().copyFrom(color); }
	default void setColor(int rgba) { getColor().setRGBA(rgba); }
	default void setColor(int r, int g, int b, int a) { getColor().setRGBA(r, g, b, a); }

	void setFX(byte fx);

	/**
	 * @return color's alpha channel
	 */
	default int getBrightness() { return getColor().getAlpha(); }

	/**
	 * Sets brightness as color's alpha channel
	 */
	default void setBrightness(int brightness) { getColor().setAlpha(brightness); }

	@Override
	default void copyEverythingFrom(IFloodlight other)
	{
		IFloodlight.super.copyEverythingFrom(other);
		if(!(other instanceof IRGBFloodlight)) return;
		IRGBFloodlight rgbOther = (IRGBFloodlight) other;

		setColor(rgbOther.getColor());
	}
}
