package lv.cecilutaka.cdtmanager2.common.threading;

public interface LoopRunnable extends Runnable
{
	void stop();
	boolean isRunning();
}
