package lv.cecilutaka.cdtmanager2.api.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IRelay;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IRGBFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.device.matrix.IRGBMatrix;

/**
 * Order is mandatory.
 */
public enum DeviceType
{
	RELAY(IRelay.class),
	BRIDGE(IBridge.class),

	MONO_FLOODLIGHT(IFloodlight.class),
	RGB_FLOODLIGHT(IRGBFloodlight.class),
	RGB_MATRIX(IRGBMatrix.class),

	UNKNOWN(Void.class);

	private static final DeviceType[] Values = values();

	private final Class<?> cls;

	DeviceType(Class<?> cls)
	{
		this.cls = cls;
	}

	public Class<?> getFloodlightClass()
	{
		return cls;
	}

	/**
	 * Same as {@link DeviceType#getTypeId()}
	 * @return firmware ID
	 */
	public int getFirmwareId()
	{
		return getTypeId();
	}

	public int getTypeId()
	{
		return ordinal();
	}

	public boolean isOfThisType(IDevice device)
	{
		return cls.isAssignableFrom(device.getClass());
	}

	/**
	 * Same as {@link DeviceType#fromTypeId(int)}
	 * @param firmwareId - firmware ID
	 * @return corresponding device type
	 */
	public static DeviceType fromFirmwareId(int firmwareId)
	{
		return fromTypeId(firmwareId);
	}

	public static DeviceType fromTypeId(int typeId)
	{
		if(typeId < 0 || typeId >= Values.length)
			return UNKNOWN;
		return Values[typeId];
	}

	public static DeviceType fromInstance(IDevice device)
	{
		for(int i = Values.length - 2; i > 0; i--) // iterate backwards (excluding UNKNOWN which should be the last element in this enum) because IDevice is superinterface of every other interface
		{
			if(Values[i].cls.isAssignableFrom(device.getClass()))
				return Values[i];
		}
		return UNKNOWN;
	}
}
