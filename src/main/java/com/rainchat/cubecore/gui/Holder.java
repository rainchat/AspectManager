package com.rainchat.cubecore.gui;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Holder implements InventoryHolder {
    private SimpleInventory gui;

    public Holder(SimpleInventory gui) {
        this.gui = gui;
    }

    @Override
    public Inventory getInventory() {
        return gui.getInventory();
    }

    public SimpleInventory getGui() {
        return gui;
    }
}