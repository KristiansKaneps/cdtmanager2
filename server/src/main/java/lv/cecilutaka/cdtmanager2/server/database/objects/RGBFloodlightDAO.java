package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.RGBFloodlightImpl;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RGBFloodlightDAO extends MonoFloodlightDAO
{
	public static void VarDumpLocal(Database db, RGBFloodlightImpl dev)
	{
		var dao = new RGBFloodlightDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();
		dao.get();

		dev.setFirmwareInfo(new FirmwareInfo(DeviceType.fromTypeId(dao.firmwareType), dao.firmware));
		dev.setUptime(dao.uptime);
		dev.setConnected(dao.connected);
		dev.setTurnedOn((dao.flags & 0x01) != 0);
		dev.setColor(dao.color);
		dev.setFX(dao.fx);
	}

	public static void VarDumpDatabase(Database db, RGBFloodlightImpl dev)
	{
		var dao = new RGBFloodlightDAO(db);
		dao.useHardwareIdAsKey(false);
		dao.id = dev.getId();

		dao.firmwareType = dev.getFirmwareInfo().getFirmwareType().getTypeId();
		dao.firmware = dev.getFirmwareInfo().getFirmware();
		dao.uptime = dev.getUptime();
		dao.connected = dev.isConnected();
		dao.flags = (byte) (dev.isTurnedOn() ? 0x01 : 0x00);
		dao.color = dev.getColor().getRGBA();
		dao.fx = dev.getFX();

		dao.update();
	}

	public volatile int color;
	public volatile byte fx;

	protected final String rgbFloodlightsTable = Database.TABLE_RGB_FLOODLIGHTS;

	protected final SingleParameterCallback[] rgbFloodlightsValues = {
			s -> s.setInt(1, id),
			s -> s.setInt(2, color),
			s -> s.setByte(3, fx)
	};

	public RGBFloodlightDAO(Database database)
	{
		super(database);
	}

	@Override
	public void update()
	{
		super.update();
		updateRGBFloodlightData();
	}

	public void updateRGBFloodlightData()
	{
		database.update(
				rgbFloodlightsTable,
				"id, color, fx",
				"?, ?, ?",
				3,
				rgbFloodlightsValues
		);
	}

	@Override
	public void get()
	{
		super.get();
		getRGBFloodlightData();
	}

	public void getRGBFloodlightData()
	{
		ResultSet result = database.execute("SELECT color, fx FROM " + rgbFloodlightsTable + " WHERE id=?;", 1, stmt -> stmt.setInt(1, id));
		if(result == null) return;

		try
		{
			color = result.getInt("color");
			fx = result.getByte("fx");
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
