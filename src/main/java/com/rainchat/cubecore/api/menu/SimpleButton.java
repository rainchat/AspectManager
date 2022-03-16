package com.rainchat.cubecore.api.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.BiConsumer;

public class SimpleButton implements Button {
        private final ItemStack itemStack;
    private final BiConsumer<UUID, InventoryClickEvent> consumer;

    public SimpleButton(ItemStack itemStack, BiConsumer<UUID, InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ItemStack getItemStack(UUID uuid) {
        return this.itemStack;
    }

    public void handleAction(UUID uuid, InventoryClickEvent event) {
        this.consumer.accept(uuid, event);
    }
}