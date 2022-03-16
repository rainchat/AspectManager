package com.rainchat.cubecore.api.action;

import co.aikar.taskchain.TaskChain;
import com.rainchat.cubecore.api.menu.MenuElement;
import com.rainchat.cubecore.gui.menu.SimpleInventory;

import java.util.UUID;

public interface Action extends MenuElement {
    /**
     * Add the executable code to taskChain
     *
     * @param uuid      the unique id
     * @param taskChain the TaskChain that needs adding
     */
    void addToTaskChain(UUID uuid, TaskChain<?> taskChain);

    /**
     * Set the menu involved in the action
     *
     * @param menu the menu
     */
    void setMenu(SimpleInventory menu);
}
