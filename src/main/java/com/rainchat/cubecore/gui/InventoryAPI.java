package com.rainchat.cubecore.gui;

import com.rainchat.cubecore.gui.listener.inventory.InventoryClickListener;
import com.rainchat.cubecore.utils.general.ServerLog;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class InventoryAPI {

    private static InventoryAPI instance;

    private final Plugin plugin;
    private final InventoryManager inventoryManager;

    private InventoryAPI(Plugin plugin) {
        this.plugin = plugin;
        this.inventoryManager = new InventoryManager(this);

        if (InventoryAPI.instance == null) {
            InventoryAPI.instance = this;

            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new InventoryClickListener(this), plugin);
        }
    }

    public static InventoryAPI getInstance(Plugin plugin) {
        return InventoryAPI.instance == null ? new InventoryAPI(plugin) : InventoryAPI.instance;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

}