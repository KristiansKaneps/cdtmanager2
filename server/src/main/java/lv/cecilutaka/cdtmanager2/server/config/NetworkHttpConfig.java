package lv.cecilutaka.cdtmanager2.server.config;

import lv.cecilutaka.cdtmanager2.api.server.config.INetworkHttpConfig;

public class NetworkHttpConfig implements INetworkHttpConfig
{
	protected String uri;

	public void setUri(String uri)
	{
		this.uri = uri;
	}

	@Override
	public String getUri()
	{
		return uri;
	}
}
