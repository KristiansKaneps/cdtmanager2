package lv.cecilutaka.cdtmanager2.api.common.device;

/**
 * Order is mandatory.
 */
public enum DeviceMessageType
{
	DEBUG,
	INFO,
	WARN,
	ERROR;

	private static final DeviceMessageType[] Values = values();

	private final int typeId;

	DeviceMessageType()
	{
		this.typeId = ordinal();
	}

	public int getTypeId()
	{
		return typeId;
	}

	public static DeviceMessageType fromTypeId(int typeId)
	{
		if(typeId < 0 || typeId >= Values.length)
			return DeviceMessageType.DEBUG;
		return Values[typeId];
	}
}
