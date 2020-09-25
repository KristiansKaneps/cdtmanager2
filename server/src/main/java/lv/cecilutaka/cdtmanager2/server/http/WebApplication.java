package lv.cecilutaka.cdtmanager2.server.http;

import io.netty.handler.codec.http.HttpMethod;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.config.NetworkWebServiceConfig;
import lv.cecilutaka.cdtmanager2.server.http.resources.*;
import org.restexpress.RestExpress;

import java.util.Collections;
import java.util.List;

/**
 * REST web service.
 */
public class WebApplication
{
	public enum ResponseType { JSON, XML };

	protected final RestExpress server;
	protected final Server serverInstance;

	protected final String hostname;
	protected final int port;
	protected final String resourceUri;
	protected final List<String> httpMethods;
	protected final ResponseType responseType;
	protected final int executorThreadCount;

	protected final String deviceUri, relayUri, bridgeUri, monoFloodlightUri, rgbFloodlightUri, rgbMatrixUri;

	protected WebApplication(Server serverInstance, NetworkWebServiceConfig config)
	{
		this.serverInstance = serverInstance;
		this.hostname = config.getHostname();
		this.port = config.getPort();
		this.resourceUri = config.getResourceUri();
		this.httpMethods = Collections.unmodifiableList(config.getHttpMethods());
		this.responseType = "xml".equalsIgnoreCase(config.getResponseType()) ? ResponseType.XML : ResponseType.JSON;
		this.executorThreadCount = config.getExecutorThreadCount();

		this.deviceUri = config.getDeviceUri();
		this.relayUri = config.getRelayUri();
		this.bridgeUri = config.getBridgeUri();
		this.monoFloodlightUri = config.getMonoFloodlightUri();
		this.rgbFloodlightUri = config.getRgbFloodlightUri();
		this.rgbMatrixUri = config.getRgbMatrixUri();

		server = new RestExpress();

		server.setExecutorThreadCount(executorThreadCount);

		final HttpMethod[] httpMethods = this.httpMethods.stream().map(HttpMethod::valueOf).toArray(HttpMethod[]::new);

		server.uri(resourceUri + deviceUri, new DeviceResource(this))
		      .method(httpMethods)
		      .performSerialization();
		server.uri(resourceUri + relayUri, new RelayResource(this))
		      .method(httpMethods)
		      .performSerialization();
		server.uri(resourceUri + bridgeUri, new BridgeResource(this))
		      .method(httpMethods)
		      .performSerialization();
		server.uri(resourceUri + monoFloodlightUri, new MonoFloodlightResource(this))
		      .method(httpMethods)
		      .performSerialization();
		server.uri(resourceUri + rgbFloodlightUri, new RGBFloodlightResource(this))
		      .method(httpMethods)
		      .performSerialization();
		server.uri(resourceUri + rgbMatrixUri, new RGBMatrixResource(this))
		      .method(httpMethods)
		      .performSerialization();
	}

	public synchronized void start()
	{
		server.bind(hostname, port);
	}

	public synchronized void stop()
	{
		server.shutdown();
	}

	public RestExpress getServer()
	{
		return server;
	}

	public final Server getServerInstance()
	{
		return serverInstance;
	}

	public String getHostname()
	{
		return hostname;
	}

	public int getPort()
	{
		return port;
	}

	public String getResourceUri()
	{
		return resourceUri;
	}

	public List<String> getHttpMethods()
	{
		return httpMethods;
	}

	public ResponseType getResponseType()
	{
		return responseType;
	}

	public int getExecutorThreadCount()
	{
		return executorThreadCount;
	}

	public static WebApplication createWebApplication(Server serverInstance, NetworkWebServiceConfig config)
	{
		return new WebApplication(serverInstance, config);
	}
}
