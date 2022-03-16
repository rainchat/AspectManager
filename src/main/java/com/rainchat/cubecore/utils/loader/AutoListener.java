package com.rainchat.cubecore.utils.loader;

import com.rainchat.cubecore.api.builder.ActionBuilder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class AutoListener implements Listener {

	protected final void register(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
