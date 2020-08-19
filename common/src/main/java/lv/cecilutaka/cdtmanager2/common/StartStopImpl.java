package lv.cecilutaka.cdtmanager2.common;

/**
 * Simple service
 */
public abstract class StartStopImpl
{
	protected volatile boolean running = false;

	protected abstract void onStart();
	protected abstract void onStop();

	public final synchronized void start()
	{
		if(running) return;
		running = true;
		onStart();
	}

	public final synchronized void stop()
	{
		if(!running) return;
		running = false;
		onStop();
	}

	public final boolean isRunning()
	{
		return running;
	}
}
