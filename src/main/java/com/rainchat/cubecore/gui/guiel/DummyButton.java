package com.rainchat.cubecore.gui.guiel;

import com.rainchat.cubecore.api.action.Action;
import com.rainchat.cubecore.api.builder.ItemModifierBuilder;
import com.rainchat.cubecore.api.item.ItemBuilder;
import com.rainchat.cubecore.api.menu.BaseWrappedButton;
import com.rainchat.cubecore.api.menu.Button;
import com.rainchat.cubecore.api.requirement.Requirement;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.event.inventory.ClickType;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DummyButton extends BaseWrappedButton {

    private final ItemBuilder itemBuilder = new ItemBuilder();
    private final Map<ClickType, List<Action>> actionMap = new HashMap<>();
    private final Map<ClickType, List<Requirement>> requestMap = new HashMap<>();

    /**
     * Create a new button
     *
     * @param menu the menu
     */
    public DummyButton(SimpleInventory menu) {
        super(menu);
    }

    @Override
    protected Button createButton(Map<String, Object> section) {
        return null;
    }

    @Override
    public void setFromSection(Map<String, Object> section) {
        ItemModifierBuilder.INSTANCE.getItemModifiers(section).forEach(itemBuilder::addItemModifier);
    }

}
