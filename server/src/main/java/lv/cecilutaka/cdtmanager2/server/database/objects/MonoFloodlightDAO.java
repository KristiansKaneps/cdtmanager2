package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.FloodlightImpl;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonoFloodlightDAO extends DeviceDAO
{
	public static void VarDumpLocal(Database db, FloodlightImpl dev)
	{
		var dao = new MonoFloodlightDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();
		dao.get();

		dev.setFirmwareInfo(new FirmwareInfo(DeviceType.fromTypeId(dao.firmwareType), dao.firmware));
		dev.setUptime(dao.uptime);
		dev.setConnected(dao.connected);
		dev.setTurnedOn((dao.flags & 0x01) != 0);
	}

	public static void VarDumpDatabase(Database db, FloodlightImpl dev)
	{
		var dao = new MonoFloodlightDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();

		dao.firmwareType = dev.getFirmwareInfo().getFirmwareType().getTypeId();
		dao.firmware = dev.getFirmwareInfo().getFirmware();
		dao.uptime = dev.getUptime();
		dao.connected = dev.isConnected();
		dao.flags = (byte) (dev.isTurnedOn() ? 0x01 : 0x00);

		dao.update();
	}

	public volatile byte flags;

	protected final String monoFloodlightsTable = Database.TABLE_MONO_FLOODLIGHTS;

	protected final SingleParameterCallback[] monoFloodlightsValues = {
			s -> s.setInt(1, id),
			s -> s.setByte(2, flags)
	};

	public MonoFloodlightDAO(Database database)
	{
		super(database);
	}

	@Override
	public void update()
	{
		super.update();
		updateMonoFloodlightData();
	}

	public void updateMonoFloodlightData()
	{
		database.update(
				monoFloodlightsTable,
				"id, flags",
				"?, ?",
				2,
				monoFloodlightsValues
		);
	}

	@Override
	public void get()
	{
		super.get();
		getMonoFloodlightData();
	}

	public void getMonoFloodlightData()
	{
		ResultSet result = database.execute("SELECT flags FROM " + monoFloodlightsTable + " WHERE id=?;", 1, stmt -> stmt.setInt(1, id));
		if(result == null) return;

		try
		{
			flags = result.getByte("flags");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try { result.close(); } catch(SQLException ignored) { }
		}
	}
}
