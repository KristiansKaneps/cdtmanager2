package lv.cecilutaka.cdtmanager2.api.common.registry;

import java.util.Collection;
import java.util.Set;

/**
 * Registry map.
 * @param <K> key type
 * @param <V> value type
 */
public interface IRegistry<K, V>
{
	/**
	 * Registry instantiation.
	 */
	void initialize();

	void register(K key, V value);
	void unregister(K key);

	/**
	 * Doesn't register empty value if key is already present
	 * @return existing value or newly created one
	 */
	RegistryValue<V> registerEmpty(K key);

	default RegistryValue<V> get(K key) { return registerEmpty(key); }

	default boolean contains(K key) { return !get(key).isEmpty(); }

	Set<K> getKeySet();
	Collection<RegistryValue<V>> getValueCollection();
}
