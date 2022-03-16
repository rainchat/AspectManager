package com.rainchat.cubecore.gui.listener.inventory;

import com.rainchat.cubecore.gui.InventoryAPI;
import com.rainchat.cubecore.utils.general.ListenerAdapter;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import com.rainchat.cubecore.gui.customevent.HInventoryClickEvent;
import com.rainchat.cubecore.gui.elements.GuiElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends ListenerAdapter {

    public InventoryClickListener(InventoryAPI inventoryAPI) {
        super(inventoryAPI);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.UNKNOWN)) {
            event.setCancelled(true);
            return;
        } else if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            SimpleInventory simpleInventory = this.inventoryManager.getPlayerInventory(player);


            if (simpleInventory == null) {
                return;
            }

            HInventoryClickEvent hInventoryClickEvent = new HInventoryClickEvent(player, simpleInventory, event);
            Bukkit.getPluginManager().callEvent(hInventoryClickEvent);
            if (hInventoryClickEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            int slot = -1;
            if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                slot = event.getRawSlot();
            } else if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                slot = event.getInventory().firstEmpty();
            }


            GuiElement.Action action = simpleInventory.getElement(slot).getAction(player);

            if (action == null || action.onClick(new GuiElement.Click(simpleInventory, slot, null, event))) {
                event.setCancelled(true);
                if (event.getWhoClicked() instanceof Player) {
                    ((Player) event.getWhoClicked()).updateInventory();
                }
            }
        }

    }

}

