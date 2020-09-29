package lv.cecilutaka.cdtmanager2.api.common.registry;

/**
 * Registry value entry with get/set callbacks.
 * @param <K> key type
 * @param <V> value type
 */
public class CallbackRegistryValue<K, V> extends RegistryValue<V>
{
	protected final SetCallback<K, V> setCallback;
	protected final GetCallback<K, V> getCallback;

	protected K key;

	protected CallbackRegistryValue(SetCallback<K, V> setCallback, GetCallback<K, V> getCallback, V value)
	{
		super(value);
		this.setCallback = setCallback;
		this.getCallback = getCallback;
	}

	@Override
	public V get()
	{
		getCallback.onGet(key, value);
		return value;
	}

	@Override
	public void set(V value)
	{
		setCallback.onSet(key, value);
		this.value = value;
	}

	/**
	 * Used only for callbacks.
	 * @param key registry key
	 */
	public void setKey(K key)
	{
		this.key = key;
	}

	@FunctionalInterface
	public interface SetCallback<K, V>
	{
		void onSet(K key, V value);
	}

	@FunctionalInterface
	public interface GetCallback<K, V>
	{
		void onGet(K key, V value);
	}
}
