package lv.cecilutaka.cdtmanager2.server.config;

import lv.cecilutaka.cdtmanager2.api.server.config.INetworkWebServiceConfig;

import java.util.List;

public class NetworkWebServiceConfig implements INetworkWebServiceConfig
{
	protected String hostname;
	protected int port;
	protected String resourceUri;
	protected List<String> httpMethods;
	protected String responseType;
	protected int executorThreadCount;

	protected String deviceUri, relayUri, bridgeUri, monoFloodlightUri, rgbFloodlightUri, rgbMatrixUri;

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public void setResourceUri(String resourceUri)
	{
		this.resourceUri = resourceUri;
	}

	public void setHttpMethods(List<String> httpMethods)
	{
		this.httpMethods = httpMethods;
	}

	public void setResponseType(String responseType)
	{
		this.responseType = responseType;
	}

	public void setExecutorThreadCount(int executorThreadCount)
	{
		this.executorThreadCount = executorThreadCount;
	}

	public void setDeviceUri(String deviceUri)
	{
		this.deviceUri = deviceUri;
	}

	public void setRelayUri(String relayUri)
	{
		this.relayUri = relayUri;
	}

	public void setBridgeUri(String bridgeUri)
	{
		this.bridgeUri = bridgeUri;
	}

	public void setMonoFloodlightUri(String monoFloodlightUri)
	{
		this.monoFloodlightUri = monoFloodlightUri;
	}

	public void setRgbFloodlightUri(String rgbFloodlightUri)
	{
		this.rgbFloodlightUri = rgbFloodlightUri;
	}

	public void setRgbMatrixUri(String rgbMatrixUri)
	{
		this.rgbMatrixUri = rgbMatrixUri;
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
	public String getResourceUri()
	{
		return resourceUri;
	}

	@Override
	public List<String> getHttpMethods()
	{
		return httpMethods;
	}

	@Override
	public String getResponseType()
	{
		return responseType;
	}

	@Override
	public int getExecutorThreadCount()
	{
		return executorThreadCount;
	}

	@Override
	public String getDeviceUri()
	{
		return deviceUri;
	}

	@Override
	public String getRelayUri()
	{
		return relayUri;
	}

	@Override
	public String getBridgeUri()
	{
		return bridgeUri;
	}

	@Override
	public String getMonoFloodlightUri()
	{
		return monoFloodlightUri;
	}

	@Override
	public String getRgbFloodlightUri()
	{
		return rgbFloodlightUri;
	}

	@Override
	public String getRgbMatrixUri()
	{
		return rgbMatrixUri;
	}
}
