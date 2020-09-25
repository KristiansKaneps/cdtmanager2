package lv.cecilutaka.cdtmanager2.server.registry;

import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;
import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;

import java.util.*;

public class DeviceReadOnlyRegistry implements IRegistry<Integer, IDevice>
{
	protected List<IRegistry<Integer, ? extends IDevice>> registryList;

	public DeviceReadOnlyRegistry()
	{
		registryList = new ArrayList<>();
	}

	public synchronized DeviceReadOnlyRegistry addSubRegistry(IRegistry<Integer, ? extends IDevice> registry)
	{
		registryList.add(registry);
		return this;
	}

	/**
	 * No-op.
	 */
	@Override
	public void register(Integer key, IDevice value)
	{

	}

	/**
	 * Unregisters given key from all sub-registries.
	 */
	@Override
	public synchronized void unregister(Integer key)
	{
		for(IRegistry<Integer, ? extends IDevice> registry : registryList)
			registry.unregister(key);
	}

	/**
	 * Returns first occurring registry value.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized RegistryValue<IDevice> registerEmpty(Integer key)
	{
		for(IRegistry<Integer, ? extends IDevice> registry : registryList)
			if(registry.contains(key)) return (RegistryValue<IDevice>) registry.get(key);
		return RegistryValue.unmodifiableEmpty();
	}

	@Override
	public boolean contains(Integer key)
	{
		for(IRegistry<Integer, ? extends IDevice> registry : registryList)
			if(registry.contains(key)) return true;
		return false;
	}

	@Override
	public synchronized Set<Integer> getKeySet()
	{
		Set<Integer> keySet = new HashSet<>();
		for(IRegistry<Integer, ? extends IDevice> registry : registryList)
			keySet.addAll(registry.getKeySet());
		return keySet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Collection<RegistryValue<IDevice>> getValueCollection()
	{
		Collection<RegistryValue<IDevice>> valueCollection = new ArrayList<>();
		for(IRegistry<Integer, ? extends IDevice> registry : registryList)
			for(RegistryValue<? extends IDevice> value : registry.getValueCollection())
				valueCollection.add((RegistryValue<IDevice>) value);
		return valueCollection;
	}
}
