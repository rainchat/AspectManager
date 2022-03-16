package com.rainchat.cubecore.api.menu;

import org.bukkit.inventory.ItemStack;

public class DummyButton extends SimpleButton {
    public DummyButton(ItemStack itemStack) {
        super(itemStack, (uuid, event) -> {
        });
    }
}
