package com.rainchat.cubecore.utils.general;

import com.rainchat.cubecore.gui.InventoryAPI;
import com.rainchat.cubecore.gui.InventoryManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerAdapter implements Listener {

    protected final Plugin plugin;
    protected final InventoryManager inventoryManager;

    public ListenerAdapter(InventoryAPI inventoryAPI) {
        this.plugin = inventoryAPI.getPlugin();
        this.inventoryManager = inventoryAPI.getInventoryManager();
    }
}
