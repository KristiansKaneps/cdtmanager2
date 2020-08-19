package lv.cecilutaka.cdtmanager2.api.server.config;

import java.io.Serializable;

public interface INetworkRestConfig extends Serializable
{
	int getPort();
	String getIp();
}
