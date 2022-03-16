package com.rainchat.cubecore.utils.objects;


import com.rainchat.cubecore.utils.general.ServerLog;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class CustomFile {

    private final String name;
    private final Plugin plugin;
    private final String fileName;
    private final String homeFolder;
    private FileConfiguration configuration;

    public CustomFile(File fileConf, String homeFolder, Plugin plugin) {
        this.name = fileConf.getName().replace(".yml", "");
        this.plugin = plugin;
        this.fileName = name;
        this.homeFolder = homeFolder;

        this.configuration = YamlConfiguration.loadConfiguration(fileConf);
    }

    /**
     * Get the name of the file without the .yml part.
     *
     * @return The name of the file without the .yml.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the full name of the file.
     *
     * @return Full name of the file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the name of the home folder of the file.
     *
     * @return The name of the home folder the files are in.
     */
    public String getHomeFolder() {
        return homeFolder;
    }

    /**
     * Get the plugin the file belongs to.
     *
     * @return The plugin the file belongs to.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get the ConfigurationFile.
     *
     * @return The ConfigurationFile of this file.
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Check if the file actually exists in the file system.
     *
     * @return True if it does and false if it doesn't.
     */
    public Boolean exists() {
        return configuration != null;
    }

    public Map<String, Object> getValues(String path, boolean deep) {
        if (path == null || path.isEmpty()) {
            return this.configuration.getValues(deep);
        } else {
            return Optional.ofNullable(this.configuration.getConfigurationSection(path))
                    .map(configurationSection -> configurationSection.getValues(deep))
                    .orElse(Collections.emptyMap());
        }
    }


    public Map<String, Object> getNormalizedValues(String path, boolean deep) {
        Map<String, Object> normalized = new LinkedHashMap<>();
        getValues(path, deep).forEach((k, v) -> normalized.put(k, normalizeObject(v)));
        return normalized;
    }

    public boolean isNormalizable(Object object) {
        return object instanceof ConfigurationSection;
    }

    public Object normalizeObject(Object object) {
        Object normalizedValue = isNormalizable(object) ? normalize(object) : object;
        if (normalizedValue instanceof Map) {
            // noinspection unchecked
            ((Map) normalizedValue).replaceAll((k, v) -> normalizeObject(v));
        } else if (normalizedValue instanceof Collection) {
            List<Object> normalizedList = new ArrayList<>();
            // noinspection unchecked
            ((Collection) normalizedValue).forEach(v -> normalizedList.add(normalizeObject(v)));
            normalizedValue = normalizedList;
        }
        return normalizedValue;
    }

    public Object normalize(Object object) {
        if (object instanceof ConfigurationSection) {
            return ((ConfigurationSection) object).getValues(false);
        }
        return object;
    }

    public Map<String, Object> getNormalizedValues(boolean deep) {
        return this.getNormalizedValues("", deep);
    }


    /**
     * Save the custom file.
     *
     * @return True if it saved correct and false if something went wrong.
     */
    public Boolean saveFile() {
        if (configuration != null) {
            try {
                configuration.save(new File(homeFolder));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Overrides the loaded state file and loads the filesystems file.
     *
     * @return True if it reloaded correct and false if the file wasn't found or errored.
     */
    public Boolean reloadFile() {
        if (configuration != null) {
            try {
                configuration = YamlConfiguration.loadConfiguration(new File(homeFolder));
                return true;
            } catch (Exception e) {
                ServerLog.error("Could not reload the " + fileName + "!");
                e.printStackTrace();
            }
        }
        return false;
    }

}