package lv.cecilutaka.cdtmanager2.server.registry;

import lv.cecilutaka.cdtmanager2.api.common.device.DeviceType;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.registry.SimpleRegistryImpl;

import java.util.Collection;
import java.util.Set;

public class MqttDeviceTypeRegistry extends SimpleRegistryImpl<Integer, DeviceType>
{
	public MqttDeviceTypeRegistry()
	{
		super();
	}

	@Override
	public synchronized void register(Integer key, DeviceType value)
	{
		super.register(key, value);
	}

	@Override
	public synchronized void unregister(Integer key)
	{
		super.unregister(key);
	}

	@Override
	public synchronized RegistryValue<DeviceType> registerEmpty(Integer key)
	{
		return super.registerEmpty(key);
	}

	@Override
	public synchronized Set<Integer> getKeySet()
	{
		return super.getKeySet();
	}

	@Override
	public synchronized Collection<RegistryValue<DeviceType>> getValueCollection()
	{
		return super.getValueCollection();
	}
}
