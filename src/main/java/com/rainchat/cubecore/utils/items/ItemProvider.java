package com.rainchat.cubecore.utils.items;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface ItemProvider {

    ItemStack build();

    ItemStack buildFor(UUID playerUUID);

}
