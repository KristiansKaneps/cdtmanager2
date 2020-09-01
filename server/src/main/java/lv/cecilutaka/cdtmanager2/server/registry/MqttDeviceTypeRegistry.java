package lv.cecilutaka.cdtmanager2.server.registry;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.registry.SimpleRegistryImpl;

import java.util.Collection;
import java.util.Set;

public class MqttDeviceTypeRegistry extends SimpleRegistryImpl<String, DeviceType>
{
	public MqttDeviceTypeRegistry()
	{
		super();
	}

	@Override
	public synchronized void register(String key, DeviceType value)
	{
		super.register(key, value);
	}

	@Override
	public synchronized void unregister(String key)
	{
		super.unregister(key);
	}

	@Override
	public synchronized RegistryValue<DeviceType> registerEmpty(String key)
	{
		return super.registerEmpty(key);
	}

	@Override
	public synchronized Set<String> getKeySet()
	{
		return super.getKeySet();
	}

	@Override
	public synchronized Collection<RegistryValue<DeviceType>> getValueCollection()
	{
		return super.getValueCollection();
	}
}
