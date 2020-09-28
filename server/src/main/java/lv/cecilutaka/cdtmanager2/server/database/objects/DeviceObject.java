package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceObject extends DbObject
{
	public volatile int id;
	public volatile int firmwareType;
	public volatile String firmware;
	public volatile int hardwareId;
	public volatile int uptime;
	public volatile boolean connected;

	protected final SingleParameterCallback[] allValues = new SingleParameterCallback[] {
			s -> s.setInt(1, firmwareType),
			s -> s.setString(2, firmware),
			s -> s.setInt(3, hardwareId),
			s -> s.setInt(4, uptime),
			s -> s.setBoolean(5, connected)
	};

	protected boolean useHardwareIdAsKey = true;

	public DeviceObject(Database database)
	{
		super(database, Database.TABLE_DEVICES);
	}

	public void useHardwareIdAsKey(boolean value)
	{
		useHardwareIdAsKey = value;
	}

	@Override
	public void update()
	{
		database.update(
				table,
				"firmware_type, firmware, hardware_id, uptime, connected",
				"?, ?, ?, ?, ?",
				5,
				allValues
		);
	}

	@Override
	public void get()
	{
		ResultSet result = database.execute("SELECT " +
		                                    "firmware_type, firmware, hardware_id, uptime, connected" +
		                                    " FROM " + table + " WHERE " + (useHardwareIdAsKey ? "hardware_id" : "id") + "=?;",
		                                    1, stmt -> stmt.setInt(1, useHardwareIdAsKey ? hardwareId : id)
		);
		if(result == null) return;

		try
		{
			firmwareType = result.getInt("firmware_type");
			firmware = result.getString("firmware");
			hardwareId = result.getInt("hardware_id");
			uptime = result.getInt("uptime");
			connected = result.getBoolean("connected");
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

	private void _copyTo(DeviceObject dest)
	{
		dest.id = id;
		dest.firmwareType = firmwareType;
		dest.firmware = firmware;
		dest.hardwareId = hardwareId;
		dest.uptime = uptime;
		dest.connected = connected;
	}

	public RelayObject asRelay()
	{
		if(firmwareType != DeviceType.RELAY.getTypeId())
			return null;

		RelayObject obj = new RelayObject(database);
		_copyTo(obj);

		return obj;
	}

	public BridgeObject asBridge()
	{
		if(firmwareType != DeviceType.BRIDGE.getTypeId())
			return null;

		BridgeObject obj = new BridgeObject(database);
		_copyTo(obj);

		return obj;
	}

	public MonoFloodlightObject asMonoFloodlight()
	{
		if(firmwareType != DeviceType.MONO_FLOODLIGHT.getTypeId())
			return null;

		MonoFloodlightObject obj = new MonoFloodlightObject(database);
		_copyTo(obj);

		return obj;
	}

	public RGBFloodlightObject asRGBFloodlight()
	{
		if(firmwareType != DeviceType.RGB_FLOODLIGHT.getTypeId())
			return null;

		RGBFloodlightObject obj = new RGBFloodlightObject(database);
		_copyTo(obj);

		return obj;
	}

	public RGBMatrixObject asRGBMatrix()
	{
		if(firmwareType != DeviceType.RGB_MATRIX.getTypeId())
			return null;

		RGBMatrixObject obj = new RGBMatrixObject(database);
		_copyTo(obj);

		return obj;
	}
}
