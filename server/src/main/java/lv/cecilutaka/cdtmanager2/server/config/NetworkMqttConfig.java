package lv.cecilutaka.cdtmanager2.server.config;

import lv.cecilutaka.cdtmanager2.api.server.config.INetworkMqttConfig;

import java.util.List;

public class NetworkMqttConfig implements INetworkMqttConfig
{
	protected int port;
	protected String hostname;
	protected boolean ssl;

	protected int autoReconnectMinDelay, autoReconnectMaxDelay;

	protected List<String> sslProtocols;

	protected String user, password;

	public void setPort(int port)
	{
		this.port = port;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public void setSsl(boolean ssl)
	{
		this.ssl = ssl;
	}

	public void setAutoReconnectMinDelay(int seconds)
	{
		this.autoReconnectMinDelay = seconds;
	}

	public void setAutoReconnectMaxDelay(int seconds)
	{
		this.autoReconnectMaxDelay = seconds;
	}

	public void setSslProtocols(List<String> sslProtocols)
	{
		this.sslProtocols = sslProtocols;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public int getPort()
	{
		return port;
	}

	@Override
	public String getHostname()
	{
		return hostname;
	}

	@Override
	public boolean getSsl()
	{
		return ssl;
	}

	@Override
	public int getAutoReconnectMinDelay()
	{
		return autoReconnectMinDelay;
	}

	@Override
	public int getAutoReconnectMaxDelay()
	{
		return autoReconnectMaxDelay;
	}

	@Override
	public List<String> getSslProtocols()
	{
		return sslProtocols;
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
}
