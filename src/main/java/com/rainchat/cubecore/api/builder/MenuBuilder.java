package com.rainchat.cubecore.api.builder;

import com.rainchat.cubecore.gui.menu.ArgsMenu;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import com.rainchat.cubecore.utils.objects.Builder;
import com.rainchat.cubecore.utils.objects.CaseInsensitiveStringMap;
import com.rainchat.cubecore.utils.objects.CustomFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.Optional;

public class MenuBuilder extends Builder<String, SimpleInventory> {

    public static final MenuBuilder INSTANCE = new MenuBuilder();

    private MenuBuilder() {
        registerDefaultMenus();
    }

    private void registerDefaultMenus() {
        register(ArgsMenu::new, "simple");
    }

    /**
     * Build the menu from the config
     *
     * @param name   the name of the menu
     * @param config the config
     *
     * @return the menu
     */
    public SimpleInventory getMenu(String name, CustomFile config) {
        Map<String, Object> keys = new CaseInsensitiveStringMap<>(config.getNormalizedValues(true));
        SimpleInventory menu = Optional.ofNullable(keys.get("menu-settings.menu-type"))
                .map(String::valueOf)
                .flatMap(string -> build(string, name))
                .orElseGet(() -> build("simple", name).orElse(null));
        if (menu != null) {
            menu.setFromConfig(config);
        }
        return menu;
    }
}

