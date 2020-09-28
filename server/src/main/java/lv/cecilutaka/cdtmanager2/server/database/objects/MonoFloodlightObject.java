package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonoFloodlightObject extends DeviceObject
{
	public volatile byte flags;

	protected final String monoFloodlightsTable = Database.TABLE_MONO_FLOODLIGHTS;

	protected final SingleParameterCallback[] monoFloodlightsValues = {
			s -> s.setInt(1, id),
			s -> s.setByte(2, flags)
	};

	public MonoFloodlightObject(Database database)
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
