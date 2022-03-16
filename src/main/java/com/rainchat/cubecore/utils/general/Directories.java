package com.rainchat.cubecore.utils.general;


import com.rainchat.cubecore.utils.loader.ResourceLoader;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class Directories {
	public final File dialogue;

	private static File initDirectory(File base, String path) {
		if (!path.endsWith("/"))
			path = path + "/";

		File result = new File(base, path);

		if (!result.exists() && !result.mkdirs())
			throw new IllegalStateException("Directory missing but cannot be created: " + result.getPath());

		return result;
	}

	public Directories(ResourceLoader resources) {
		File base = resources.getBaseDir();
		ConfigurationSection config;
		try {
			config = resources.loadConfig("config.yml");
		}
		catch (Exception e) {
			throw new IllegalStateException("Failed to load config.yml", e);
		}

		dialogue = initDirectory(base, "extension");
	}

	public static File[] listFiles(File in) {

		File[] files = in.listFiles();

		if (files != null)
			return files;

		return new File[0];
	}

	public void processFilesFromFolder(File folder)
	{
		int i = 0;
		File[] folderEntries = folder.listFiles();
		for (File entry : folderEntries)
		{
			if (entry.isDirectory())
			{
				processFilesFromFolder(entry);
				continue;
			}
			ServerLog.info(i + " " + entry.getName());
			i++;
		}
	}

	public static File[] listFiles(File in, FileFilter filter) {
		File[] files = in.listFiles(filter);

		if (files != null)
			return files;

		return new File[0];
	}

	public static File[] listFiles(File in, FilenameFilter filter) {
		File[] files = in.listFiles(filter);

		if (files != null)
			return files;

		return new File[0];
	}
}
