package lv.cecilutaka.cdtmanager2.server;

public final class Main
{
	private Main() { }

	public static void main(String[] args)
	{
		Server server = new Server();

		Runtime.getRuntime().addShutdownHook(new Thread(server::stop, "CDTManager2 Shutdown Hook Thread"));

		server.start();
	}
}
