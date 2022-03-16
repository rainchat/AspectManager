package com.rainchat.cubecore.api;

import co.aikar.commands.PaperCommandManager;
import com.rainchat.cubecore.gui.InventoryAPI;
import com.rainchat.cubecore.managers.PlaceholderManager;
import com.rainchat.cubecore.utils.loader.ExtensionInstaller;
import com.rainchat.cubecore.utils.objects.Reloadable;
import org.bukkit.plugin.Plugin;



public interface CubeApi extends Reloadable {


    Plugin getPlugin();

    PaperCommandManager getCommandManager();

    PlaceholderManager getPlaceholderManager();

    ExtensionInstaller getExtensions();

    InventoryAPI getInventoryAPI();
}
