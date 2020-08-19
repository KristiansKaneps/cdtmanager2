package lv.cecilutaka.cdtmanager2.api.common.registry;

/**
 * Unmodifiable empty registry value
 * @param <T> value type
 */
public class EmptyRegistryValue<T> extends RegistryValue<T>
{
	protected EmptyRegistryValue()
	{
		super(null);
	}

	@Override
	public void set(T value)
	{

	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}
}
