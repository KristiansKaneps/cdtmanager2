package lv.cecilutaka.cdtmanager2.api.server.database;

import java.sql.SQLException;

public interface IDatabase
{
	void connect() throws SQLException;
	void disconnect();

	/**
	 * Create tables if they don't exist.
	 */
	void createTables() throws SQLException;

	/**
	 * Checks if the server is connected to database.
	 */
	boolean isConnected();
}
