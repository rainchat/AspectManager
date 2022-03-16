package com.rainchat.cubecore.api.menu;

import com.rainchat.cubecore.gui.menu.Menu;
import com.rainchat.cubecore.gui.menu.SimpleInventory;

public interface MenuElement {
    /**
     * Get the menu containing the element
     *
     * @return the menu
     */
    SimpleInventory getMenu();
}