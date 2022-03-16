package com.rainchat.cubecore.gui.menu;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public abstract class Menu {

    private final String name;

    /**
     * Create a new menu
     *
     * @param name the name of the menu
     */
    protected Menu(String name) {
        this.name = name;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Called when setting options
     *
     * @param config the config of the menu
     */
    public abstract void setFromConfig(FileConfiguration config);

    /**
     * Called when opening the menu for the player
     *
     * @param player the player involved in
     * @param args   the arguments from the open command
     * @param bypass whether the plugin ignores the permission check
     *
     * @return Whether it's successful
     */
    public abstract boolean show(Player player, String[] args, boolean bypass);

    /**
     * Called when updating the menu
     *
     * @param player the player involved in
     */
    public abstract void updateInventory(Player player);

    /**
     * Close the inventory
     *
     * @param player the player involved in
     */
    public abstract void closeInventory(Player player);

    /**
     * Close/Clear all inventories of the type
     */
    public abstract void closeAll();

}