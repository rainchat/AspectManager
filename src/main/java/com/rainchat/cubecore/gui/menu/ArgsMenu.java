package com.rainchat.cubecore.gui.menu;

import com.rainchat.cubecore.CubeCorePlugin;
import com.rainchat.cubecore.api.CubeCore;
import com.rainchat.cubecore.gui.InventoryCreator;
import com.rainchat.cubecore.gui.InventoryManager;
import com.rainchat.cubecore.gui.elements.GuiElement;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.ServerLog;
import com.rainchat.cubecore.utils.objects.CaseInsensitiveStringMap;
import com.rainchat.cubecore.utils.objects.CustomFile;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgsMenu extends SimpleInventory {

    private Inventory inventory;

    private InventoryCreator creator;
    private InventoryManager inventoryManager;

    private String title;
    private final Map<Character, GuiElement> elements = new HashMap<>();
    private InventoryType inventoryType;

    /**
     * Create a new menu
     *
     * @param name the name of the menu
     */
    public ArgsMenu(String name) {
        super(name);
    }

    public void setFromConfig(CustomFile config) {
        String[] guiSetup = {
                "  s i z  ",
                "  ggggg  ",
                "  fpdnl  "
        };

        config.getNormalizedValues(false).forEach((key, value) -> {
            if (!(value instanceof Map)) {
                return;
            }
            ServerLog.info(key);
            Map<String, Object> values = new CaseInsensitiveStringMap<>((Map<String, Object>) value);


            for (Map.Entry<String, Object> entry: values.entrySet()) {
                ServerLog.warning(entry.getKey() + " " + entry.getValue());
            }
        });



        setup(CubeCore.getPlugin(), Chat.translateRaw(config.getConfiguration().getString("menu-settings.title")), guiSetup);


    }

    public boolean show(Player player, String[] args, boolean bypass) {
        return false;
    }

    public void updateInventory(Player player) {

    }

    public void closeInventory(Player player) {

    }

    public void closeAll() {

    }
}
