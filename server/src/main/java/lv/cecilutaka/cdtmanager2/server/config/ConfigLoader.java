package lv.cecilutaka.cdtmanager2.server.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

import java.io.*;
import java.util.Objects;

import static lv.cecilutaka.cdtmanager2.common.file.FileLoader.getFileInJarDirectory;

public class ConfigLoader
{
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public Config load(String configFilename)
	{
		Config baseConfig = ConfigFactory.load(configFilename);
		File configFile = getFileInJarDirectory(configFilename);
		if(!configFile.exists())
		{
			try {
				if (!configFile.getParentFile().exists())
					configFile.getParentFile().mkdirs();
				configFile.createNewFile();
				try(
						BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ConfigLoader.class.getClassLoader().getResourceAsStream(configFilename))));
						BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))
				) {
					String line;
					while((line = reader.readLine()) != null)
					{
						writer.write(line);
						writer.write('\n');
					}
				}
			} catch (IOException e) { e.printStackTrace(); }
		}
		Config config = ConfigFactory.parseFile(configFile).withFallback(baseConfig);
		config.checkValid(baseConfig);
		return config;
	}

	public <T> T load(Config config, Class<T> beanClass)
	{
		return ConfigBeanFactory.create(config, beanClass);
	}

	public void reload()
	{
		ConfigFactory.invalidateCaches();
	}
}
