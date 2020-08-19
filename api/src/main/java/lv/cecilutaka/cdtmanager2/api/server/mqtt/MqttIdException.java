package lv.cecilutaka.cdtmanager2.api.server.mqtt;

public class MqttIdException extends Exception
{
	public MqttIdException()
	{
		super();
	}

	public MqttIdException(String message)
	{
		super(message);
	}

	public MqttIdException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
