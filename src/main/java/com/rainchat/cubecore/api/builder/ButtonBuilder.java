package com.rainchat.cubecore.api.builder;

import com.rainchat.cubecore.api.menu.WrappedButton;
import com.rainchat.cubecore.gui.guiel.DummyButton;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import com.rainchat.cubecore.utils.objects.Builder;
import com.rainchat.cubecore.utils.objects.CaseInsensitiveStringMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ButtonBuilder extends Builder<SimpleInventory, WrappedButton> {

    /**
     * The instance of the button builder
     */
    public static final ButtonBuilder INSTANCE = new ButtonBuilder();

    private ButtonBuilder() {
        registerDefaultButtons();
    }

    private void registerDefaultButtons() {
        register(DummyButton::new, "dummy");
    }

    /**
     * Build the button from the section
     *
     * @param menu    the menu
     * @param name    the name of the button
     * @param section the section
     *
     * @return the button
     */
    public WrappedButton getButton(SimpleInventory menu, String name, Map<String, Object> section) {
        Map<String, Object> keys = new CaseInsensitiveStringMap<>(section);
        WrappedButton button = Optional.ofNullable(keys.get("type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, menu))
                .orElseGet(() -> build("dummy", menu).orElse(null));
        if (button != null) {
            button.setName(name);
            button.setFromSection(section);
        }
        return button;
    }

    /**
     * Get the child buttons from the parent button
     *
     * @param parentButton the parent button
     * @param section      the child section
     *
     * @return the child buttons
     */
    public List<WrappedButton> getChildButtons(WrappedButton parentButton, Map<String, Object> section) {
        return section.entrySet()
                .stream()
                .filter(entry -> entry.getValue() instanceof Map)
                .map(entry -> {
                    // noinspection unchecked
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    return getButton(parentButton.getMenu(), parentButton.getName() + "_child_" + entry.getKey(), value);
                })
                .collect(Collectors.toList());
    }
}