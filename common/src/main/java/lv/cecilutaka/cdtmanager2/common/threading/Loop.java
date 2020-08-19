package lv.cecilutaka.cdtmanager2.common.threading;

public class Loop
{
	protected Thread thread;

	protected final LoopRunnable runnable;
	protected final String name;

	public Loop(LoopRunnable runnable, String name)
	{
		this.runnable = runnable;
		this.name = name.toLowerCase().endsWith("thread") ? name : name + " Thread";
	}

	public boolean isRunning()
	{
		return runnable.isRunning();
	}

	public synchronized void startSync()
	{
		if(isRunning()) return;

		thread = Thread.currentThread();
		runnable.run();
	}

	public synchronized void startAsync()
	{
		if(isRunning()) return;
		thread = new Thread(runnable, name);
		thread.start();
	}

	public synchronized void stop()
	{
		if(!isRunning()) return;
		runnable.stop();
	}

	public Thread getThread()
	{
		return thread;
	}

	public String getName()
	{
		return name;
	}

	public static void sleep(long millis)
	{
		try { Thread.sleep(millis); } catch (InterruptedException ignored) { }
	}
}
