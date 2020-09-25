package lv.cecilutaka.cdtmanager2.common.registry;

import lv.cecilutaka.cdtmanager2.api.common.registry.IRegistry;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;

import java.util.*;

/**
 * Simple registry implementation
 * @param <K> key type
 * @param <V> value type
 */
public class SimpleRegistryImpl<K, V> implements IRegistry<K, V>
{
	protected final Map<K, RegistryValue<V>> map;

	public SimpleRegistryImpl()
	{
		this.map = new HashMap<>();
	}

	@Override
	public synchronized void register(K key, V value)
	{
		map.put(key, RegistryValue.<V>encapsulate(value));
	}

	@Override
	public synchronized void unregister(K key)
	{
		map.remove(key);
	}

	@Override
	public synchronized RegistryValue<V> registerEmpty(K key)
	{
		if(map.containsKey(key))
			return map.get(key);

		RegistryValue<V> emptyValue = RegistryValue.<V>empty();
		map.put(key, emptyValue);
		return emptyValue;
	}

	@Override
	public boolean contains(K key)
	{
		return map.containsKey(key);
	}

	@Override
	public synchronized Set<K> getKeySet()
	{
		return Collections.unmodifiableSet(map.keySet());
	}

	@Override
	public synchronized Collection<RegistryValue<V>> getValueCollection()
	{
		return Collections.unmodifiableCollection(map.values());
	}
}
