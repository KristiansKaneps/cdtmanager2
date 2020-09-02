package lv.cecilutaka.cdtmanager2.api.common.device;

public interface IDeviceUptimeMessage
{
	/**
	 * @return uptime in seconds
	 */
	int getUptime();

	default double getUptimeInMinutes() { return getUptime() / 60.0d; }

	default double getUptimeInHours() { return getUptimeInMinutes() / 60.0d; }

	default double getUptimeInDays() { return getUptimeInHours() / 24.0d; }
}
