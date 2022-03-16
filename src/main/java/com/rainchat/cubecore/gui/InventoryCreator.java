package com.rainchat.cubecore.gui;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryCreator {
    private final CreatorImplementation<InventoryType> typeCreator;
    private final CreatorImplementation<Integer> sizeCreator;

    /**
     * A new inventory creator which should be able to create an inventory based on the type and the size.
     * <br><br>
     * By default the creators are implemented as follows:
     * <pre>
     * typeCreator = (gui, who, type) -> plugin.getServer().createInventory(new Holder(gui), type, gui.replaceVars(who, title));
     * sizeCreator = (gui, who, size) -> plugin.getServer().createInventory(new Holder(gui), size, gui.replaceVars(who, title));
     * </pre>
     * @param typeCreator The type creator.
     * @param sizeCreator The size creator
     */
    public InventoryCreator(CreatorImplementation<InventoryType> typeCreator, CreatorImplementation<Integer> sizeCreator) {
        this.typeCreator = typeCreator;
        this.sizeCreator = sizeCreator;
    }

    public CreatorImplementation<InventoryType> getTypeCreator() {
        return typeCreator;
    }

    public CreatorImplementation<Integer> getSizeCreator() {
        return sizeCreator;
    }

    public interface CreatorImplementation<T> {
        /**
         * Creates a new inventory
         * @param gui   The InventoryGui instance
         * @param who   The player to create the inventory for
         * @param t     The size or type of the inventory
         * @return      The created inventory
         */
        Inventory create(SimpleInventory gui, HumanEntity who, T t);
    }
}