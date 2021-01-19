package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.common.device.FirmwareInfo;
import lv.cecilutaka.cdtmanager2.common.device.matrix.RGBMatrixImpl;
import lv.cecilutaka.cdtmanager2.server.database.Database;

public class RGBMatrixDAO extends RGBFloodlightDAO
{
	public static void VarDumpLocal(Database db, RGBMatrixImpl dev)
	{
		var dao = new RGBMatrixDAO(db);
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

	public static void VarDumpDatabase(Database db, RGBMatrixImpl dev)
	{
		var dao = new RGBMatrixDAO(db);
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

	protected final String rgbMatricesTable = Database.TABLE_RGB_MATRICES;

	protected final SingleParameterCallback[] rgbMatricesValue = {
			s -> s.setInt(1, id)
	};

	public RGBMatrixDAO(Database database)
	{
		super(database);
	}

	@Override
	public void update()
	{
		super.update();
		updateRGBMatrixData();
	}

	public void updateRGBMatrixData()
	{
		database.update(
				rgbMatricesTable,
				"id",
				"?",
				1,
				rgbMatricesValue
		);
	}

	@Override
	public void get()
	{
		super.get();
		getRGBMatrixData();
	}

	public void getRGBMatrixData()
	{

	}
}
