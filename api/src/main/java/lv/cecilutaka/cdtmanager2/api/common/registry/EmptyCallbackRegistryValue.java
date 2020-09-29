package lv.cecilutaka.cdtmanager2.api.common.registry;

/**
 * Unmodifiable empty registry value with get/set callbacks.
 * @param <K> key type
 * @param <V> value type
 */
public class EmptyCallbackRegistryValue<K, V> extends CallbackRegistryValue<K, V>
{
	protected EmptyCallbackRegistryValue(SetCallback<K, V> setCallback, GetCallback<K, V> getCallback)
	{
		super(setCallback, getCallback, null);
	}

	@Override
	public void set(V value)
	{
		setCallback.onSet(key, value);
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}
}
