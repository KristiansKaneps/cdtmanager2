package lv.cecilutaka.cdtmanager2.server.database.objects;

import lv.cecilutaka.cdtmanager2.api.server.database.IDatabaseObject;
import lv.cecilutaka.cdtmanager2.server.database.Database;

public abstract class DbObject implements IDatabaseObject
{
	protected final Database database;
	protected final String table;

	public DbObject(Database database, String table)
	{
		this.database = database;
		this.table = table;
	}

	@Override
	public String getTable()
	{
		return table;
	}
}
