package lv.cecilutaka.cdtmanager2.api.common.registry;

/**
 * Registry value entry.
 * @param <T> value type
 */
public class RegistryValue<T>
{
	public static <V> RegistryValue<V> unmodifiableEmpty()
	{
		return new EmptyRegistryValue<>();
	}

	public static <V> RegistryValue<V> empty()
	{
		return new RegistryValue<>(null);
	}

	public static <V> RegistryValue<V> encapsulate(V value)
	{
		return new RegistryValue<>(value);
	}

	public static <K, V> CallbackRegistryValue<K, V> unmodifiableEmptyWithCallbacks(CallbackRegistryValue.SetCallback<K, V> onSet, CallbackRegistryValue.GetCallback<K, V> onGet)
	{
		return new EmptyCallbackRegistryValue<>(onSet, onGet);
	}

	public static <K, V> CallbackRegistryValue<K, V> emptyWithCallbacks(CallbackRegistryValue.SetCallback<K, V> onSet, CallbackRegistryValue.GetCallback<K, V> onGet)
	{
		return new CallbackRegistryValue<>(onSet, onGet, null);
	}

	public static <K, V> CallbackRegistryValue<K, V> encapsulateWithCallbacks(V value, CallbackRegistryValue.SetCallback<K, V> onSet, CallbackRegistryValue.GetCallback<K, V> onGet)
	{
		return new CallbackRegistryValue<>(onSet, onGet, value);
	}

	protected T value;

	protected RegistryValue(T value)
	{
		this.value = value;
	}

	public T get()
	{
		return value;
	}

	@SuppressWarnings("unchecked")
	public <C> C as()
	{
		return (C) get();
	}

	public void set(T value)
	{
		this.value = value;
	}

	public boolean isEmpty()
	{
		return value == null;
	}
}
