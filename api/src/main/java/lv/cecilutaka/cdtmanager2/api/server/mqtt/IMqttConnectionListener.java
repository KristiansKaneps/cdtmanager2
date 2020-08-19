package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public interface IMqttConnectionListener
{
	void onConnected(IMqttClient client);
	void onDisconnected(IMqttClient client);
}
