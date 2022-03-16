package com.rainchat.cubecore.api.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface Button extends Initializable {
    Button EMPTY = new DummyButton((ItemStack)null);

    ItemStack getItemStack(UUID var1);

    void handleAction(UUID var1, InventoryClickEvent var2);

    default boolean forceSetAction(UUID uuid) {
        return false;
    }
}
