package com.rainchat.cubecore.api.requirement;

import com.rainchat.cubecore.api.menu.MenuElement;
import com.rainchat.cubecore.gui.menu.SimpleInventory;

import java.util.UUID;

public interface Requirement extends MenuElement {

    /**
     * Called when checking a unique id
     *
     * @param uuid the unique id
     *
     * @return true if the unique id meets the requirement, otherwise false
     */
    boolean check(UUID uuid);

    /**
     * Called when taking the requirements from unique id
     *
     * @param uuid the unique id
     */
    void take(UUID uuid);

    /**
     * Set the value
     *
     * @param value the value
     */
    void setValue(Object value);

    /**
     * Get the name of the requirement
     *
     * @return the name
     */
    String getName();

    /**
     * Set the menu involved in
     *
     * @param menu the menu
     */
    void setMenu(SimpleInventory menu);
}

