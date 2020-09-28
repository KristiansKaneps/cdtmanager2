package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.server.database.SingleParameterCallback;
import lv.cecilutaka.cdtmanager2.server.database.Database;

public class RGBMatrixObject extends RGBFloodlightObject
{
	protected final String rgbMatricesTable = Database.TABLE_RGB_MATRICES;

	protected final SingleParameterCallback[] rgbFloodlightsValues = { };

	public RGBMatrixObject(Database database)
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
