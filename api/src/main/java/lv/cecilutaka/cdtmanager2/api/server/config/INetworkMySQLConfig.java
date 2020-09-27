package lv.cecilutaka.cdtmanager2.api.server.config;

import java.io.Serializable;

public interface INetworkMySQLConfig extends Serializable
{
	String getHostname();
	int getPort();
	String getUser();
	String getPassword();
	String getDatabase();
}
