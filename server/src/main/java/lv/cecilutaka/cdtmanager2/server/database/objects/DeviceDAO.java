package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.server.database.ParameterCallback;
import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lv.cecilutaka.cdtmanager2.server.database.Database.TABLE_DEVICES;

public class DeviceDAO extends DbDAO
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

	public DeviceDAO(Database database)
	{
		super(database, TABLE_DEVICES);
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

	/**
	 * Doesn't automatically retrieve data from row. You should use {@link DeviceDAO#get()}
	 *
	 * @param database {@link Database} object
	 * @return list of all objects in {@link Database#TABLE_DEVICES} table
	 */
	public static List<DeviceDAO> all(Database database)
	{
		return database.execute(result -> {
			if (result == null) return Collections.emptyList();

			try
			{
				List<DeviceDAO> list = new ArrayList<>();

				while (result.next())
				{
					DeviceDAO obj = new DeviceDAO(database);
					obj.id = result.getInt("id");
					obj.useHardwareIdAsKey(false);
					list.add(obj);
				}

				return Collections.unmodifiableList(list);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try { result.close(); } catch (SQLException ignored) { }
			}

			return Collections.emptyList();
		}, "SELECT id FROM " + TABLE_DEVICES + ";", 0, (ParameterCallback) null);
	}

	@Override
	public void get()
	{
		database.execute(result -> {
							 if (result == null) return null;

							 try
							 {
								 if (useHardwareIdAsKey)
									 hardwareId = result.getInt("hardware_id");
								 else
									 id = result.getInt("id");
								 firmwareType = result.getInt("firmware_type");
								 firmware = result.getString("firmware");
								 uptime = result.getInt("uptime");
								 connected = result.getBoolean("connected");
							 }
							 catch (SQLException e)
							 {
								 e.printStackTrace();
							 }
							 finally
							 {
								 try { result.close(); } catch (SQLException ignored) { }
							 }
							 return null;
						 }, "SELECT " + (useHardwareIdAsKey ? "id, " : "hardware_id, ") +
							"firmware_type, firmware, uptime, connected" +
							" FROM " + table + " WHERE " + (useHardwareIdAsKey ? "hardware_id" : "id") + "=?;",
						 1, stmt -> stmt.setInt(1, useHardwareIdAsKey ? hardwareId : id)
		);

	}

	/**
	 * Updates local ID - {@link DeviceDAO#id}
	 */
	public void getId()
	{
		database.execute(result -> {
							 if (result == null)
							 {
								 Log.e("Device", "Could not assign device's ID from database!");
								 return null;
							 }

							 try
							 {
								 id = result.getInt("id");
							 }
							 catch (SQLException e)
							 {
								 e.printStackTrace();
							 }
							 finally
							 {
								 try { result.close(); } catch (SQLException ignored) { }
							 }
							 return null;
						 }, "SELECT id FROM " + table + " WHERE hardware_id=?;",
						 1,
						 s -> s.setInt(1, hardwareId));

	}

	/**
	 * Updates local hardware ID - {@link DeviceDAO#hardwareId}
	 */
	public void getHardwareId()
	{
		database.execute(result -> {
							 if (result == null)
							 {
								 Log.e("Device", "Could not assign device's hardware ID from database!");
								 return null;
							 }

							 try
							 {
								 hardwareId = result.getInt("hardware_id");
							 }
							 catch (SQLException e)
							 {
								 e.printStackTrace();
							 }
							 finally
							 {
								 try { result.close(); } catch (SQLException ignored) { }
							 }
							 return null;
						 }, "SELECT hardware_id FROM " + table + " WHERE id=?;",
						 1,
						 s -> s.setInt(1, id));

	}

	private void _copyTo(DeviceDAO dest)
	{
		dest.id = id;
		dest.firmwareType = firmwareType;
		dest.firmware = firmware;
		dest.hardwareId = hardwareId;
		dest.uptime = uptime;
		dest.connected = connected;
	}

	public RelayDAO asRelay()
	{
		if (firmwareType != DeviceType.RELAY.getTypeId())
			return null;

		RelayDAO obj = new RelayDAO(database);
		_copyTo(obj);

		return obj;
	}

	public BridgeDAO asBridge()
	{
		if (firmwareType != DeviceType.BRIDGE.getTypeId())
			return null;

		BridgeDAO obj = new BridgeDAO(database);
		_copyTo(obj);

		return obj;
	}

	public MonoFloodlightDAO asMonoFloodlight()
	{
		if (firmwareType != DeviceType.MONO_FLOODLIGHT.getTypeId())
			return null;

		MonoFloodlightDAO obj = new MonoFloodlightDAO(database);
		_copyTo(obj);

		return obj;
	}

	public RGBFloodlightDAO asRGBFloodlight()
	{
		if (firmwareType != DeviceType.RGB_FLOODLIGHT.getTypeId())
			return null;

		RGBFloodlightDAO obj = new RGBFloodlightDAO(database);
		_copyTo(obj);

		return obj;
	}

	public RGBMatrixDAO asRGBMatrix()
	{
		if (firmwareType != DeviceType.RGB_MATRIX.getTypeId())
			return null;

		RGBMatrixDAO obj = new RGBMatrixDAO(database);
		_copyTo(obj);

		return obj;
	}
}
