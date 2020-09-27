package lv.cecilutaka.cdtmanager2.server.config;

import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMySQLConfig;

public class NetworkMySQLConfig implements INetworkMySQLConfig
{
	protected String hostname;
	protected int port;
	protected String user;
	protected String password;
	protected String database;

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setDatabase(String database)
	{
		this.database = database;
	}

	@Override
	public String getHostname()
	{
		return hostname;
	}

	@Override
	public int getPort()
	{
		return port;
	}

	@Override
	public String getUser()
	{
		return user;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getDatabase()
	{
		return database;
	}
}
