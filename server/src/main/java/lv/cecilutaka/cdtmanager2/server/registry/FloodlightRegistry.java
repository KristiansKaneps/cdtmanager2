package lv.cecilutaka.cdtmanager2.server.registry;

import lv.cecilutaka.cdtmanager2.api.common.device.floodlight.IFloodlight;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.registry.SimpleRegistryImpl;

import java.util.Collection;
import java.util.Set;

public class FloodlightRegistry extends SimpleRegistryImpl<Integer, IFloodlight>
{
	public FloodlightRegistry()
	{
		super();
	}

	@Override
	public synchronized void register(Integer key, IFloodlight value)
	{
		super.register(key, value);
	}

	@Override
	public synchronized void unregister(Integer key)
	{
		super.unregister(key);
	}

	@Override
	public synchronized RegistryValue<IFloodlight> registerEmpty(Integer key)
	{
		return super.registerEmpty(key);
	}

	@Override
	public synchronized Set<Integer> getKeySet()
	{
		return super.getKeySet();
	}

	@Override
	public synchronized Collection<RegistryValue<IFloodlight>> getValueCollection()
	{
		return super.getValueCollection();
	}
}
