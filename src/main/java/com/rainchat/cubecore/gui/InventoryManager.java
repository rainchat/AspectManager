package com.rainchat.cubecore.gui;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private InventoryAPI inventoryAPI;
    private final Plugin plugin;
    private final Map<String, SimpleInventory> playerInventoryMap = new HashMap<>();

    public InventoryManager(InventoryAPI inventoryAPI) {
        this.inventoryAPI = inventoryAPI;
        this.plugin = inventoryAPI.getPlugin();
    }

    public Map<String, SimpleInventory> getPlayerInventoryMap() {
        return this.playerInventoryMap;
    }

    public SimpleInventory getPlayerInventory(String playerName) {
        return this.playerInventoryMap.get(playerName);
    }

    public SimpleInventory getPlayerInventory(Player player) {
        return this.getPlayerInventory(player.getName());
    }

    public void setPlayerInventory(String playerName, SimpleInventory simpleInventory) {
        this.playerInventoryMap.put(playerName, simpleInventory);
    }

    public void setPlayerInventory(Player player, SimpleInventory simpleInventory) {
        this.setPlayerInventory(player.getName(), simpleInventory);
    }

}