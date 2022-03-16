package com.rainchat.cubecore;


import co.aikar.commands.PaperCommandManager;
import com.rainchat.cubecore.api.CubeApi;
import com.rainchat.cubecore.api.CubeCore;
import com.rainchat.cubecore.api.builder.MenuBuilder;
import com.rainchat.cubecore.gui.InventoryAPI;
import com.rainchat.cubecore.hooks.PlaceholderAPIBridge;
import com.rainchat.cubecore.hooks.VaultEcoHook;
import com.rainchat.cubecore.hooks.VaultPermsHook;
import com.rainchat.cubecore.managers.ConfigSettings;
import com.rainchat.cubecore.managers.FileManager;
import com.rainchat.cubecore.managers.PlaceholderManager;
import com.rainchat.cubecore.resourse.CubeCommand;
import com.rainchat.cubecore.utils.general.Message;
import com.rainchat.cubecore.utils.general.ServerLog;
import com.rainchat.cubecore.utils.loader.ExtensionInstaller;
import com.rainchat.cubecore.utils.loader.ExtensionLoader;
import com.rainchat.cubecore.utils.objects.CustomFile;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Level;

public class CubeImpl implements CubeApi {


    private final CubeCorePlugin plugin;

    private final ExtensionInstaller extensions;
    private final PaperCommandManager commandManager;
    private final FileManager fileManager;
    public PlaceholderManager placeholderManager;
    public InventoryAPI inventoryAPI;

    public CubeImpl(CubeCorePlugin plugin) {
        this.plugin = plugin;
        ServerLog.setup(plugin);

        this.fileManager = FileManager.getInstance();
        this.extensions = new ExtensionInstaller(plugin, this);
        this.commandManager = new PaperCommandManager(plugin);
        placeholderManager = new PlaceholderManager();



        //==================== File manager =====================
        fileManager.logInfo(true)
                .registerCustomFilesFolder("/language")
                .registerDefaultGenerateFiles("Ru_ru.yml", "/language", "/language")
                .setup(getPlugin());
        //=======================================================
        File file = new File(plugin.getDataFolder(), "menu");
        file.mkdirs();
        File file2 = new File(plugin.getDataFolder(), "modules");
        file.mkdirs();

        CubeCore.setAPI(this);
    }

    public void load() {

        ServerLog.info("Registered " + registerHooks() + " hook(s).");

        inventoryAPI = InventoryAPI.getInstance(plugin);
        ConfigSettings.setup();
        loadMessages();

        commandManager.registerCommand(new CubeCommand());

        placeholderManager.registerDefaultActons();



        ExtensionLoader extLoader = new ExtensionLoader(getClass().getClassLoader(),  new File(plugin.getDataFolder() + File.separator + "modules"));
        extensions.addAll(extLoader.loadLocal());
        extensions.onEnable();
    }

    public void loadMessages() {
        Message.setConfiguration(FileManager.getInstance().getFile(ConfigSettings.LANGUAGE));
        ServerLog.log(Level.INFO, "Registered " + Message.addMissingMessages() + " message(s).");
    }

    public int registerHooks() {
        int index = 0;
        PlaceholderAPIBridge placeholderAPIBridge = new PlaceholderAPIBridge();
        placeholderAPIBridge.setupPlugin();
        if (PlaceholderAPIBridge.hasValidPlugin()) {
            ServerLog.info("Successfully hooked into PlaceholderAPI.");
            index += 1;
        }
        if (VaultEcoHook.setupEconomy()) {
            ServerLog.info("Successfully hooked into Economy vault.");
            index += 1;
        } else {
            ServerLog.warning("Vault with a compatible economy plugin was not found!");

        }
        if (VaultPermsHook.setup()) {
            ServerLog.info("Successfully hooked into perms vault.");
            index += 1;
        }

        return index;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    @Override
    public ExtensionInstaller getExtensions() {
        return extensions;
    }

    @Override
    public InventoryAPI getInventoryAPI() {
        return inventoryAPI;
    }

    @Override
    public void onDiscard() {
    }

    @Override
    public void onSave() {
    }

    @Override
    public void onReload() {
        FileManager.getInstance().reloadAllFiles();
        //helpManager.update();

        ConfigSettings.setup();
        loadMessages();

        extensions.onReload();
    }

    @Override
    public void onEnable() {

    }


}
