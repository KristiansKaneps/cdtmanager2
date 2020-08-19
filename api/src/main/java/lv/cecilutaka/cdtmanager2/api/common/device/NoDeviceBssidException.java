package lv.cecilutaka.cdtmanager2.api.common.device;

public class NoDeviceBssidException extends Exception
{
	public NoDeviceBssidException()
	{
		super();
	}

	public NoDeviceBssidException(String message)
	{
		super(message);
	}

	public NoDeviceBssidException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

