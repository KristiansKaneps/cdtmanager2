package lv.cecilutaka.cdtmanager2.api.server.config;

import java.io.Serializable;
import java.util.List;

public interface INetworkMqttConfig extends Serializable
{
	int getPort();
	String getIp();
	boolean getSsl();

	int getAutoReconnectMinDelay();
	int getAutoReconnectMaxDelay();

	List<String> getSslProtocols();

	String getUser();
	String getPassword();
}
