package lv.cecilutaka.cdtmanager2.server.database;

import lv.cecilutaka.cdtmanager2.api.server.database.IDatabase;
import lv.cecilutaka.cdtmanager2.api.server.database.ParameterCallback;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMySQLConfig;

import java.sql.*;
import java.util.Properties;

public class Database implements IDatabase
{
	public static final String TABLE_DEVICES = "devices";
	//public static final String TABLE_RELAYS = "relays";
	//public static final String TABLE_BRIDGES = "bridges";
	public static final String TABLE_MONO_FLOODLIGHTS = "mono_floodlights";
	public static final String TABLE_RGB_FLOODLIGHTS = "rgb_floodlights";
	public static final String TABLE_RGB_MATRICES = "rgb_matrices";

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
				"jdbc:mysql://" + hostname + ":" + port + "/" + database
					+ "?connectTimeout=0&socketTimeout=0&autoReconnect=true",
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
		      + "  id              INT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,"
		      + "  firmware_type   SMALLINT UNSIGNED NOT NULL,"
		      + "  firmware        VARCHAR(48) NULL CHARACTER SET ascii COLLATE ascii_general_ci,"
		      + "  hardware_id     INT UNSIGNED NOT NULL UNIQUE,"
		      + "  uptime          INT DEFAULT 0,"
		      + "  connected       BOOL default 0,"
		      + "  PRIMARY KEY (id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_MONO_FLOODLIGHTS + "("
		      + "  id              INT UNSIGNED NOT NULL UNIQUE,"
		      + "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      + "  FOREIGN KEY (id) REFERENCES " + TABLE_DEVICES + "(id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_RGB_FLOODLIGHTS + "("
		      + "  id              INT UNSIGNED NOT NULL UNIQUE,"
		      //+ "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      + "  color           INT UNSIGNED DEFAULT 0,"
		      + "  fx              TINYINT UNSIGNED DEFAULT 0,"
		      + "  FOREIGN KEY (id) REFERENCES " + TABLE_DEVICES + "(id)"
		      + ") ENGINE=INNODB  DEFAULT CHARSET=utf8;"
		);

		_stmt("CREATE TABLE IF NOT EXISTS " + TABLE_RGB_MATRICES + "("
		      + "  id              INT UNSIGNED NOT NULL UNIQUE,"
		      //+ "  flags           TINYINT UNSIGNED NOT NULL DEFAULT 0,"
		      //+ "  color           INT UNSIGNED DEFAULT 0,"
		      //+ "  fx              TINYINT UNSIGNED DEFAULT 0,"
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
	public synchronized ResultSet execute(String query, int parameterLength, ParameterCallback parameterCallback)
	{
		try(PreparedStatement stmt = connection.prepareStatement(query))
		{
			for(int i = 0; i < parameterLength; i++)
				parameterCallback.mapParameter(stmt, i);
			return stmt.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public synchronized void update(String table, String columns, String values, int valueLength, ParameterCallback valueCallback)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" (");
		sb.append(columns);
		sb.append(") VALUES (");
		sb.append(values);
		sb.append(") ON DUPLICATE KEY UPDATE ");
		if(!columns.contains(", "))
		{
			sb.append(columns);
			sb.append(" = VALUES (");
			sb.append(columns);
		}
		else
		{
			String[] columnArr = columns.split(", ");
			for(int i = 0; i < columnArr.length; i++)
			{
				sb.append(columnArr[i]);
				sb.append(" = VALUES (");
				sb.append(columnArr[i]);
				if(i < columnArr.length - 1)
					sb.append("), ");
			}
		}
		sb.append(");");

		try(PreparedStatement stmt = connection.prepareStatement((sb.toString())))
		{
			for(int i = 0; i < valueLength; i++)
				valueCallback.mapParameter(stmt, i);
			stmt.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public synchronized boolean isConnected()
	{
		try { return connection != null && !connection.isClosed(); } catch(Exception e) { return false; }
	}
}
