package lv.cecilutaka.cdtmanager2.api.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDatabase
{
	void connect() throws SQLException;
	void disconnect();

	/**
	 * Create tables if they don't exist.
	 */
	void createTables() throws SQLException;

	ResultSet execute(String query, int parameterLength, ParameterCallback parameterCallback);
	default ResultSet execute(String query, int parameterLength, SingleParameterCallback... parameterCallbacks) {
		return this.execute(query, parameterLength, (statement, index) -> parameterCallbacks[index].mapParameter(statement));
	}
	default ResultSet execute(String query) {
		return this.execute(query, 0, (ParameterCallback) null);
	}

	/**
	 * Updates or inserts a row.
	 */
	void update(String table, String columns, String values, int valueLength, ParameterCallback valueCallback);
	default void update(String table, String columns, String values, int valueLength, SingleParameterCallback... valueCallbacks) {
		this.update(table, columns, values, valueLength, (statement, index) -> valueCallbacks[index].mapParameter(statement));
	}
	default void update(String table, String columns, String values) {
		this.update(table, columns, values, 0, (ParameterCallback) null);
	}

	/**
	 * Checks if the server is connected to database.
	 */
	boolean isConnected();
}
