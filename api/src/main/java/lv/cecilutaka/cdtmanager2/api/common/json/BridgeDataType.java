package lv.cecilutaka.cdtmanager2.api.common.json;

public enum BridgeDataType
{
	MESH_TOPOLOGY,

	UNKNOWN;

	private static final BridgeDataType[] Values = BridgeDataType.values();

	public int toId()
	{
		return ordinal();
	}

	public static BridgeDataType fromId(int id)
	{
		if(id < 0 || id > Values.length - 1) return UNKNOWN;
		return Values[id];
	}
}
