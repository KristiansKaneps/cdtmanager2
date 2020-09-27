package lv.cecilutaka.cdtmanager2.api.common.device.floodlight;

import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;

public interface IFloodlight extends IDevice
{
	boolean isTurnedOn();
	default boolean isTurnedOff() { return !isTurnedOn(); }

	/**
	 * Should be used only when copying parameters from other floodlight.
	 * @param id - floodlight ID
	 */
	@Override
	void setId(int id);

	void setTurnedOn(boolean turnedOn);
	default void turnOn() { setTurnedOn(true); }
	default void turnOff() { setTurnedOn(false); }

	/**
	 * Instead of registering a new floodlight object in the registry, you can copy all values from other floodlights.
	 * @param other - floodlight to copy values from
	 */
	default void copyEverythingFrom(IFloodlight other)
	{
		setId(other.getId());
		setTurnedOn(other.isTurnedOn());
		setFirmwareInfo(other.getFirmwareInfo());
	}
}
