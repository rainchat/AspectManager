package com.rainchat.cubecore.managers;


import org.bukkit.configuration.file.FileConfiguration;

public class ConfigSettings {


    public static String LANGUAGE;

    public static void setup() {
        FileConfiguration yaml = FileManager.Files.CONFIG.getFile();
        LANGUAGE = yaml.getString("Settings.language");

    }

}
