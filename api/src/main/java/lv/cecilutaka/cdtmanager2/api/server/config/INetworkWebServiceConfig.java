package lv.cecilutaka.cdtmanager2.api.server.config;

import java.io.Serializable;
import java.util.List;

public interface INetworkWebServiceConfig extends Serializable
{
	String getHostname();
	int getPort();

	String getResourceUri();
	List<String> getHttpMethods();

	/**
	 * Should be "json" or "xml"
	 */
	String getResponseType();

	int getExecutorThreadCount();

	String getDeviceUri();
	String getRelayUri();
	String getBridgeUri();
	String getMonoFloodlightUri();
	String getRgbFloodlightUri();
	String getRgbMatrixUri();
}
