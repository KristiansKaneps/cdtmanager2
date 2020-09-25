package lv.cecilutaka.cdtmanager2.server.http.resources.json;

import lv.cecilutaka.cdtmanager2.api.common.device.IFirmwareInfo;

public class JsonFirmware
{
	public int type;
	public String typeName;
	public String value;

	public JsonFirmware(IFirmwareInfo firmwareInfo)
	{
		type = firmwareInfo.getFirmwareType().getTypeId();
		typeName = firmwareInfo.getFirmwareType().name();
		value = firmwareInfo.getFirmware();
	}
}
