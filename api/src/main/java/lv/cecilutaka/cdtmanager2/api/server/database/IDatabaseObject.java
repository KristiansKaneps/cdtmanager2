package lv.cecilutaka.cdtmanager2.api.server.database;

public interface IDatabaseObject
{
	String getTable();

	/**
	 * Updates database entry.
	 */
	void update();

	/**
	 * Updates local variables from database.
	 */
	void get();
}
