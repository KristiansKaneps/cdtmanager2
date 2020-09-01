package lv.cecilutaka.cdtmanager2.server.registry;

import lv.cecilutaka.cdtmanager2.api.common.device.bridge.IBridge;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.registry.SimpleRegistryImpl;

import java.util.Collection;
import java.util.Set;

public class BridgeRegistry extends SimpleRegistryImpl<Integer, IBridge>
{
	public BridgeRegistry()
	{
		super();
	}

	@Override
	public synchronized void register(Integer key, IBridge value)
	{
		super.register(key, value);
	}

	@Override
	public synchronized void unregister(Integer key)
	{
		super.unregister(key);
	}

	@Override
	public synchronized RegistryValue<IBridge> registerEmpty(Integer key)
	{
		return super.registerEmpty(key);
	}

	@Override
	public synchronized Set<Integer> getKeySet()
	{
		return super.getKeySet();
	}

	@Override
	public synchronized Collection<RegistryValue<IBridge>> getValueCollection()
	{
		return super.getValueCollection();
	}
}