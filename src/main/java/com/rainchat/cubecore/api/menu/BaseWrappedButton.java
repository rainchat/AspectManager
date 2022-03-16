package com.rainchat.cubecore.api.menu;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public abstract class BaseWrappedButton implements WrappedButton {

    private final SimpleInventory menu;
    protected Button button;
    private String name = "";

    /**
     * Create a new button
     *
     * @param menu the menu
     */
    protected BaseWrappedButton(SimpleInventory menu) {
        this.menu = menu;
    }

    /**
     * Create the button from the section
     *
     * @param section the section
     *
     * @return the button
     */
    protected abstract Button createButton(Map<String, Object> section);

    @Override
    public void setFromSection(Map<String, Object> section) {
        this.button = createButton(section);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public SimpleInventory getMenu() {
        return menu;
    }

    @Override
    public ItemStack getItemStack(UUID uuid) {
        if (button != null) {
            return button.getItemStack(uuid);
        }
        return null;
    }

    @Override
    public void handleAction(UUID uuid, InventoryClickEvent event) {
        if (button != null) {
            button.handleAction(uuid, event);
        }
    }

    @Override
    public void init() {
        if (button != null) {
            button.init();
        }
    }

    @Override
    public void stop() {
        if (button != null) {
            button.stop();
        }
    }
}