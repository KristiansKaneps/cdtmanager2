package lv.cecilutaka.cdtmanager2.common.log;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Log
{
	private static boolean printDebug = true;

	public static void debug(boolean on)
	{
		printDebug = on;
	}

	public static PrintStream PRINT_STREAM = System.out;
	public static PrintStream DEBUG_PRINT_STREAM = System.out;
	public static PrintStream ERROR_PRINT_STREAM = System.err;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private static final String DEBUG = "[DEBUG]";
	private static final String INFO = "[INFO]";
	private static final String WARN = "[WARN]";
	private static final String ERR = "[ERROR]";
	private static final String FATAL =  " [FATAL]";

	private Log() {}

	protected static void o(Object o, PrintStream stream)
	{
		stream.println('[' + sdf.format(new Date()) + "] " + o.toString());
	}

	protected static void o(Object o, boolean err)
	{
		o(o, err ? ERROR_PRINT_STREAM : PRINT_STREAM);
	}

	protected static void o(Object prefix, Object o, PrintStream stream)
	{
		o(prefix.toString() + ' ' + o.toString(), stream);
	}

	protected static void o(Object prefix, Object o, boolean err)
	{
		o(prefix.toString(), o.toString(), err ? ERROR_PRINT_STREAM : PRINT_STREAM);
	}

	public static void d(Object debug)
	{
		if(!printDebug) return;
		o(DEBUG, debug, DEBUG_PRINT_STREAM);
	}

	public static void d(Object prefix, Object debug)
	{
		if(!printDebug) return;
		o(DEBUG + " [" + prefix + ']', debug, DEBUG_PRINT_STREAM);
	}

	public static void i(Object info)
	{
		o(INFO, info, false);
	}

	public static void i(Object prefix, Object info)
	{
		o(INFO + " [" + prefix + ']', info, false);
	}

	public static void w(Object warn)
	{
		o(WARN, warn, false);
	}

	public static void w(Object prefix, Object warn)
	{
		o(WARN + " [" + prefix + ']', warn, false);
	}

	public static void e(Object err)
	{
		o(ERR, err, true);
	}

	public static void e(Object prefix, Object err)
	{
		o(ERR + " [" + prefix + ']', err, true);
	}

	public static void f(Object fatal)
	{
		o(FATAL, fatal, true);
	}

	public static void f(Object prefix, Object fatal)
	{
		o(FATAL + " [" + prefix + ']', fatal, true);
	}
}
