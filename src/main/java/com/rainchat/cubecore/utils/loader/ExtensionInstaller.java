package com.rainchat.cubecore.utils.loader;


import com.rainchat.cubecore.CubeImpl;
import com.rainchat.cubecore.managers.FileManager;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.ServerLog;
import com.rainchat.cubecore.utils.objects.Reloadable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ExtensionInstaller extends AutoListener implements Reloadable {
	private final List<CoreExtension> extensions = new ArrayList<>();

	private final Plugin plugin;
	private final CubeImpl api;

	public ExtensionInstaller(Plugin plugin, CubeImpl api) {
		this.plugin = plugin;
		this.api = api;
		register(plugin);
	}

	@Override
	public void onSave() {
		for (CoreExtension extension : extensions) {

			String name = extensionName(extension);
			try {
				extension.onSave();
			}
			catch (Throwable e) {
				ServerLog.warning("Error saving extension: " + name);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onReload() {
		for (CoreExtension extension : extensions) {
			reloadModule(extension);
		}
	}

	public void reloadModule(CoreExtension module) {

		if (module.isInitialized()) {
			close(module);
		}

		module.setEnabled(FileManager.Files.MODULE.getFile().getBoolean("modules." + module.getName()));

		if (module.isEnabled() && module.isReady()) {
			initialize(module, module.getName());
		}

		System.out.println(module.getName() + " was reloaded. It is: " + module.isEnabled());
	}

	public void loadModule(CoreExtension module) {
		module.onEnable();
	}

	public void close(CoreExtension module) {
		module.unregister();
	}

	@Override
	public void onEnable() {

		FileConfiguration file = FileManager.Files.MODULE.getFile();
		for (CoreExtension module : extensions) {

			if (file.contains("modules." + module.getName())) {
				module.setEnabled(file.getBoolean("modules." + module.getName()));
			} else {
				file.set("modules." + module.getName(), true);
				module.setEnabled(true);
			}
			FileManager.Files.MODULE.saveFile();

			if (!module.isEnabled()) {
				ServerLog.info(Chat.translateRaw("MODULES " + module.getName() + " was not registered"));
			} else {
				ServerLog.info(Chat.translateRaw("MODULES " + module.getName() + " was registered"));
				//initialize(module);
			}
		}
	}


	@Override
	public void onDiscard() {
		for (CoreExtension extension : extensions) {
			String name = extensionName(extension);
			try {
				extension.onDiscard();
			}
			catch (Throwable e) {
				ServerLog.warning("Error discarding extension: " + name);
				e.printStackTrace();
			}
		}
	}

	public List<CoreExtension> getActiveExtensions() {
		return Collections.unmodifiableList(extensions);
	}

	public List<CoreExtension> getInactiveExtensions() {
		return Collections.unmodifiableList(extensions);
	}

	public void add(CoreExtension extension) {
		extensions.add(extension);
	}

	public void addAll(Collection<CoreExtension> extensions) {
		for (CoreExtension extension : extensions) {
			add(extension);
		}
	}


	public void initialize(CoreExtension extension) {
		extension.onEnable();
	}

	private void initialize(CoreExtension extension, String name) {
		if (extension.isInitialized()) {
			ServerLog.warning("Error initializing extension: " + name + ": Double initializationS!");
			return;
		}

		ServerLog.info("Installer - Initializing extension: " + name);

		try {
			extension.init(plugin);
		}
		catch (Throwable e) {
			ServerLog.warning("Error initializing extension: " + name);
			e.printStackTrace();
			return;
		}

		PluginManager pm = plugin.getServer().getPluginManager();

	}

	private String extensionName(CoreExtension extension) {
		String name;
		try {
			name = extension.getName();
		}
		catch (Throwable e) {
			name = extension.getClass().getSimpleName();
			ServerLog.warning("Error getting extension name for class " + name);
			e.printStackTrace();
		}

		return name;
	}


	@EventHandler
	public void onPluginEnable(PluginEnableEvent event) {

		for (CoreExtension extension : extensions) {
			extension.enablePlugin(event.getPlugin());

			if (extension.isEnabled && extension.isReady() && !extension.isInitialized()) {
				String name = extensionName(extension);
				ServerLog.info("Installer - Dependencies loaded: " + name);
				initialize(extension, name);
				//loadExtension(extension);
			}
		}
	}

}
