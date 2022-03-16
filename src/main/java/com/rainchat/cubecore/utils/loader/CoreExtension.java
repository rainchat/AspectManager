package com.rainchat.cubecore.utils.loader;


import co.aikar.commands.BaseCommand;
import com.rainchat.cubecore.api.CubeApi;
import com.rainchat.cubecore.api.CubeCore;
import com.rainchat.cubecore.utils.objects.CustomFile;
import com.rainchat.cubecore.utils.objects.Reloadable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Only abstract because nobody should make a raw instance of this.
public abstract class CoreExtension implements Reloadable {

    private List<BaseCommand> commands = new ArrayList<>();
    private List<Listener> listenerList = new ArrayList<>();
    private boolean initialized = false;
    private CubeApi api = CubeCore.getAPI();
    private int remaining;
    private final String[] requirements;
    private final Plugin[] found;
    private File direction;
    private ResourceLoader loader;


    protected boolean isEnabled;
    protected boolean isLoaded;

    protected String name;
    protected String description;
    protected String modulePrefix;

    /**
     * Performs setup for this extension. Do not assume plugin dependencies have
     * been loaded at this point.
     */

    public CoreExtension(String... requirements) {
        this.requirements = requirements.clone();
        remaining = requirements.length;
        found = new Plugin[remaining];

    }

    public CubeApi getApi() {
        return api;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public void setLoader(ResourceLoader loader) {
        this.loader = loader;
    }

    /**
     * Supplies the extension name, primarily used for printing status info.
     * <p>
     * The default value is the simple name of the class.
     *
     * @return The extension name
     */
    @Control
    public String getName() {
        return name == null ? getClass().getSimpleName(): name;
    }

    /**
     * This must return a list of all plugins that the hook depends on.
     *
     * @return A list of all names of plugin dependencies
     */
    public final String[] getDepends() {
        return requirements.clone();
    }

    public final void setDirection(File file) {
        direction = file;
    }

    public final FileConfiguration getConfig() {
        try {
            return loader.loadConfig(direction, "config.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public final File[] getFolder(String fileName) {
        return loader.getFoldersFile(direction, fileName);
    }

    public final FileConfiguration getCustomConfig(String path) {
        return loader.loadConfig(direction, path);
    }

    public final FileConfiguration getCustomConfiguration(String fileName, String jarHomeFolder) {
        return loader.loadConfig(direction, fileName, jarHomeFolder);
    }

    public final CustomFile getCustomFile(String fileName, String homeFolder, String jarHomeFolder) {
        return loader.loadCustomFile(direction, fileName, homeFolder, jarHomeFolder);
    }

    public final ResourceLoader getResourceLoader() {
        return loader;
    }

    @Control
    @Override
    public void onReload() {
    }

    @Control
    @Override
    public void onSave() {
    }


    @Control
    public void initialize(Plugin parent) {
    }

    @Control
    protected void disable() {
    }

    /**
     * Initializes the extension after all dependencies have been located. This is
     * an internal function and should not be called directly.
     *
     * @throws Throwable Any (likely unchecked) exception raised by
     *                   {@link CoreExtension#initialize initialize} will be passed up
     *                   the stack
     */
    public final void init(Plugin parent) {
        if (initialized || !isReady())
            return;

        onEnable();

        initialized = true;
    }

    public final void unregister() {
        onDiscard();
        unregisterCommands();
        unregisterListeners();

        initialized = false;
    }

    /**
     * Only use this if you know what you are doing! Assigns a plugin to a specific
     * index in the resolved dependencies.
     *
     * @param plugin The plugin matching a dependency
     * @param index  The index of the match
     */
    public final boolean directEnablePlugin(Plugin plugin, int index) {
        if (!plugin.getName().equals(requirements[index]) || found[index] != null)
            return false;

        found[index] = plugin;
        --remaining;
        return true;
    }

    /**
     * Only use this if you know what you are doing! Tries to add a plugin to the
     * resolved dependencies. If the plugin name matches a dependency, the plugin is
     * added to resolved dependencies
     *
     * @param plugin The plugin we are attempting to match
     */
    public final boolean enablePlugin(Plugin plugin) {
        for (int i = 0; i < requirements.length; ++i)
            if (directEnablePlugin(plugin, i))
                return true;

        return false;
    }


    public void registerCommand(BaseCommand executor) {
        api.getCommandManager().registerCommand(executor);
        commands.add(executor);
    }

    public void registerListener(Listener listener) {
        PluginManager pm = api.getPlugin().getServer().getPluginManager();
        pm.registerEvents(listener, api.getPlugin());
        listenerList.add(listener);
    }

    public void unregisterListeners() {
        for (Listener listener : listenerList) {
            HandlerList.unregisterAll(listener);
        }
    }

    public void unregisterCommands() {
        for (BaseCommand command : commands) {
            api.getCommandManager().unregisterCommand(command);
        }
    }

    /**
     * Checks if all dependencies have been loaded.
     *
     * @return true if all dependencies have been loaded, false otherwise
     */
    public final boolean isReady() {
        return remaining <= 0;
    }

    public final boolean isInitialized() {
        return initialized;
    }

    /**
     * Gets the resolved dependencies. The order matches the order of
     * {@link #getDepends}.
     *
     * @return The list of resolved dependencies
     */
    public final Plugin[] getPlugins() {
        if (!isReady())
            throw new IllegalStateException("Attempted to get plugin references before they were resolved");

        return found.clone();
    }
}
