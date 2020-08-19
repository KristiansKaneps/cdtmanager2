package lv.cecilutaka.cdtmanager2.common.file;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader
{
	protected final File dir;

	public FileLoader()
	{
		this.dir = getJarDirectory();
	}

	public FileLoader(String folder)
	{
		this.dir = new File(getJarDirectory().getPath() + getFileSeparator() + folder);
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public File getFile(String filename) throws IOException
	{
		File file = new File(dir.getPath() + getFileSeparator() + filename);
		if(!file.exists())
		{
			if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}

	public void write(String filename, boolean append, String... lines) throws IOException
	{
		write(getFile(filename), append, lines);
	}

	public void write(File file, boolean append, String... lines) throws IOException
	{
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, append)))
		{
			for(String line : lines)
			{
				writer.write(line);
				writer.write('\n');
			}
		}
	}

	public String read(String filename) throws IOException
	{
		return read(getFile(filename));
	}

	public String read(File file) throws IOException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			StringBuilder sb = new StringBuilder();
			String line;

			while((line = reader.readLine()) != null)
			{
				sb.append(line);
				sb.append('\n');
			}

			return sb.toString();
		}
	}

	public List<String> readLines(String filename) throws IOException
	{
		return readLines(getFile(filename));
	}

	public List<String> readLines(File file) throws IOException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			List<String> lines = new ArrayList<>();
			String line;

			while((line = reader.readLine()) != null)
			{
				lines.add(line);
			}

			return lines;
		}
	}

	public void overwrite(String filename, String... lines) throws IOException
	{
		overwrite(getFile(filename), lines);
	}

	public void overwrite(File file, String... lines) throws IOException
	{
		write(file, false, lines);
	}

	public void append(String filename, String... lines) throws IOException
	{
		append(getFile(filename), lines);
	}

	public void append(File file, String... lines) throws IOException
	{
		write(file, true, lines);
	}

	public static File getJarDirectory()
	{
		try
		{
			return new File(FileLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		throw new RuntimeException("Could not get jar's directory");
	}

	public static File getFileInJarDirectory(String filename)
	{
		try
		{
			return new File(new File(FileLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath() + getFileSeparator() + filename);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		throw new RuntimeException("Could not get jar's directory");
	}

	public static String getFileSeparator()
	{
		return File.separator;
	}
}
