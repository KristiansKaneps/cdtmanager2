package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.server.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RGBFloodlightObject extends MonoFloodlightObject
{
	public volatile int color;
	public volatile byte fx;

	protected final String rgbFloodlightsTable = Database.TABLE_RGB_FLOODLIGHTS;

	protected final SingleParameterCallback[] rgbFloodlightsValues = {
			s -> s.setInt(1, id),
			s -> s.setInt(2, color),
			s -> s.setByte(3, fx)
	};

	public RGBFloodlightObject(Database database)
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
