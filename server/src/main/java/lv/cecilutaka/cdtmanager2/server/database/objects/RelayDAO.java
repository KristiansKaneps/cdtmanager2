package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.device.bridge.RelayImpl;
import lv.cecilutaka.cdtmanager2.server.database.Database;

public class RelayDAO extends DeviceDAO
{
	public static void VarDumpLocal(Database db, RelayImpl dev)
	{
		var dao = new RelayDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();
		dao.get();

		dev.setFirmwareInfo(new FirmwareInfo(DeviceType.fromTypeId(dao.firmwareType), dao.firmware));
		dev.setUptime(dao.uptime);
		dev.setConnected(dao.connected);
	}

	public static void VarDumpDatabase(Database db, RelayImpl dev)
	{
		var dao = new RelayDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();

		dao.firmwareType = dev.getFirmwareInfo().getFirmwareType().getTypeId();
		dao.firmware = dev.getFirmwareInfo().getFirmware();
		dao.uptime = dev.getUptime();
		dao.connected = dev.isConnected();

		dao.update();
	}

	public RelayDAO(Database database)
	{
		super(database);
	}
}
