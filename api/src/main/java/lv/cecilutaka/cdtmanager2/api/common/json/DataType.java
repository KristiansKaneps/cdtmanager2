package lv.cecilutaka.cdtmanager2.api.common.json;

public enum DataType
{
	REQUEST,
	BROAD_REQUEST,
	MESSAGE,
	FIRMWARE_INFO,
	UPTIME,
	COLOR,
	COLOR_EFFECT,
	FVPOOL_CREATE,
	FVPOOL_SET,

	UNKNOWN;

	private static final DataType[] Values = DataType.values();

	public int toId()
	{
		return ordinal() + 40;
	}

	public static DataType fromId(int id)
	{
		if(id < 40 || id > 39 + Values.length) return UNKNOWN;
		return Values[id - 40];
	}
}
