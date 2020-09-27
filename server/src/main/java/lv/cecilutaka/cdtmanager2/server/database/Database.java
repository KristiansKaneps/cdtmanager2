package lv.cecilutaka.cdtmanager2.server.database;

import lv.cecilutaka.cdtmanager2.api.server.database.IDatabase;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMySQLConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database implements IDatabase
{
	private static final String TABLE_DEVICES = "devices";
	//private static final String TABLE_RELAYS = "relays";
	//private static final String TABLE_BRIDGES = "bridges";
	private static final String TABLE_MONO_FLOODLIGHTS = "mono_floodlights";
	private static final String TABLE_RGB_FLOODLIGHTS = "rgb_floodlights";
	private static final String TABLE_RGB_MATRICES = "rgb_matrices";

	private static final String TABLE_UPTIMES = "uptimes";

	private Connection connection;

	protected final Server server;

	private final String hostname;
	private final int port;
	private final String user;
	private final String password;
	private final String database;

	public Database(Server server, NetworkMySQLConfig config)
	{
		this.server = server;

		this.hostname = config.getHostname();
		this.port = config.getPort();
		this.user = config.getUser();
		this.password = config.getPassword();
		this.database = config.getDatabase();
	}

	@Override
	public synchronized void connect() throws SQLException
	{
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", user);
		connectionProperties.put("password", password);
		DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + database,
				connectionProperties
		);
	}

	@Override
	public synchronized void disconnect()
	{
		if(connection == null) return;
		try { connection.close(); } catch(Exception ignored) { } finally { connection = null; }
	}

	@Override
	public synchronized void createTables() throws SQLException
	{
		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_DEVICES + "("
		      + "  id              INT UNSIGNED NOT NULL AUTO_INCREMENT,"
		      + "  firmware_type   SMALLINT UNSIGNED NOT NULL,"
		      + "  firmware        VARCHAR(64) NULL,"
		      + "  PRIMARY KEY (id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_MONO_FLOODLIGHTS + "("
		      + "  id              INT UNSIGNED NOT NULL,"
		      + "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      + "  FOREIGN KEY (id) REFERENCES " + TABLE_DEVICES + "(id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_RGB_FLOODLIGHTS + "("
		      + "  id              INT UNSIGNED NOT NULL,"
		      //+ "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      + "  color           INT UNSIGNED DEFAULT 0,"
		      + "  FOREIGN KEY (id) REFERENCES " + TABLE_DEVICES + "(id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_RGB_MATRICES + "("
		      + "  id              INT UNSIGNED NOT NULL,"
		      //+ "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      //+ "  color           INT UNSIGNED DEFAULT 0,"
		      + "  FOREIGN KEY (id) REFERENCES " + TABLE_DEVICES + "(id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);
	}

	private synchronized void _stmt(String sql) throws SQLException
	{
		Statement stmt = connection.createStatement();
		stmt.execute(sql);
		stmt.close();
	}

	@Override
	public synchronized boolean isConnected()
	{
		try { return connection != null && !connection.isClosed(); } catch(Exception e) { return false; }
	}
}
