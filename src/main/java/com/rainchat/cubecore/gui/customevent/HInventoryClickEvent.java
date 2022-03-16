package com.rainchat.cubecore.gui.customevent;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HInventoryClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final SimpleInventory simpleInventory;
    private final InventoryClickEvent event;
    private boolean cancelled = false;

    public HInventoryClickEvent(Player player, SimpleInventory simpleInventory, InventoryClickEvent event) {
        this.player = player;
        this.simpleInventory = simpleInventory;
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public SimpleInventory getInventory() {
        return this.simpleInventory;
    }

    public InventoryClickEvent getClickEvent() {
        return this.event;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
