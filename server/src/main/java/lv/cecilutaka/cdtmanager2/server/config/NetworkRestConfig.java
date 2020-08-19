package lv.cecilutaka.cdtmanager2.server.config;

import lv.cecilutaka.cdtmanager2.api.server.config.INetworkRestConfig;

public class NetworkRestConfig implements INetworkRestConfig
{
	protected int port;
	protected String ip;

	public void setPort(int port)
	{
		this.port = port;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	@Override
	public int getPort()
	{
		return port;
	}

	@Override
	public String getIp()
	{
		return ip;
	}
}
