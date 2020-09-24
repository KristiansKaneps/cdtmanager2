package junit;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lv.cecilutaka.cdtmanager2.server.config.NetworkMqttConfig;
import lv.cecilutaka.cdtmanager2.server.config.NetworkHttpConfig;
import org.junit.Test;

public class TestConfig
{
	@Test
	public void loadConfig()
	{
		Config config = ConfigFactory.load("network.conf");

		assert config != null;

		NetworkMqttConfig netMqttConfig = ConfigBeanFactory.create(config.getConfig("mqtt"), NetworkMqttConfig.class);
		NetworkHttpConfig netRestConfig = ConfigBeanFactory.create(config.getConfig("rest"), NetworkHttpConfig.class);

		assert netMqttConfig != null;
		assert netRestConfig != null;

		int port = netMqttConfig.getPort();
		String ip = netMqttConfig.getIp();

		System.out.println("netMqttConfig.getPort() = " + port);
		System.out.println("netMqttConfig.getIp() = " + ip);

		assert port > 0;
		assert ip != null;

		port = netRestConfig.getPort();
		ip = netRestConfig.getIp();

		System.out.println("netRestConfig.getPort() = " + port);
		System.out.println("netRestConfig.getIp() = " + ip);

		assert port > 0;
		assert ip != null;
	}
}
