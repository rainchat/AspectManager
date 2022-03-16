package com.rainchat.cubecore.api.menu;

import java.util.Map;
import java.util.UUID;

public interface WrappedButton extends Button, MenuElement {

    /**
     * Called when setting options
     *
     * @param section the section of that button in the config
     */
    void setFromSection(Map<String, Object> section);

    /**
     * Get the name of the button
     *
     * @return the name
     */
    String getName();

    /**
     * Set the name of the button
     *
     * @param name the name
     */
    void setName(String name);

    /**
     * Refresh the button for the unique id
     *
     * @param uuid the unique id
     */
    default void refresh(UUID uuid) {
        // EMPTY
    }
}
