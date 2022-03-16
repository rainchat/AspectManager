package com.rainchat.cubecore.utils.general;

import com.rainchat.cubecore.managers.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum Message {
    PREFIX("Preffix", "&f[&aAspectManager&f] "),


    ERROR_UNKNOWN_COMMAND("Message.error.unknown_command","&4Unknown command. Type &c/karma help &4for help."),
    ERROR_NO_PERMISSION("Message.error.no-permissions","&4You don't have the permission to use this command!"),
    ERROR_UNKNOWN_PLAYER("Message.error.unknown-player", "&4The specified player is not online."),
    ERROR_INVALID_NUMBER("Message.error.invalid-number","&4The number you have entered is invalid."),


    NO_COMMAND_PERMISSION("Messages.general.no-command-Permissions", "&cУ вас нет прав на использование"),
    PLAYER_OFFLINE("Messages.general.player-offline", "Игрок &b%player_name% &7не в сети."),
    
    NO_PERMISSION("Message.global.No-Permissions", "&cУ вас нет прав чтобы использовать эту команду"),
    RELOAD("Message.global.Reload", "&7Все конфиги перезагружены.."),
    SAVE("Message.global.Save", "&7Данные игроков успешно сохранены..");


    private static FileManager.GeneralFile configuration;
    private final String path;
    private String def;
    private List<String> list;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    public static int addMissingMessages() {
        FileConfiguration file = configuration.getFile();
        int index = 0;

        boolean saveFile = false;
        for (Message message : values()) {
            index++;
            if (!file.contains(message.getPath())) {
                saveFile = true;
                if (message.getDefaultMessage() != null) {
                    file.set(message.getPath(), message.getDefaultMessage());
                } else {
                    file.set(message.getPath(), message.getDefaultListMessage());
                }
            }
        }
        if (saveFile) {
            configuration.saveFile();
        }


        return index;
    }

    public static String convertList(List<String> list) {
        String message = "";
        for (String line : list) {
            message += line + "\n";
        }
        return message;
    }

    public static void setConfiguration(FileManager.GeneralFile configuration) {
        Message.configuration = configuration;
    }

    public String getDef() {
        return configuration.getFile().getString(path, def);
    }

    @Override
    public String toString() {
        String message;
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(configuration.getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = configuration.getFile().getString(path);
            } else {
                message = getDefaultMessage();
            }
        }

        return Color.parseHexString(Chat.translateRaw(message));
    }

    public String getMessage() {
        String message;
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(configuration.getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = configuration.getFile().getString(path);
            } else {
                message = getDefaultMessage();
            }
        }

        return Color.parseHexString(Chat.translateRaw(message));
    }

    private boolean exists() {
        return configuration.getFile().contains(path);
    }

    private boolean isList() {
        if (configuration.getFile().contains(path)) {
            return !configuration.getFile().getStringList(path).isEmpty();
        } else {
            return def == null;
        }
    }

    public List<String> toList() {
        return configuration.getFile().getStringList(path);
    }

    public String getPath() {
        return path;
    }

    public List<String> getList() {
        return list;
    }

    private String getDefaultMessage() {
        return def;
    }

    private List<String> getDefaultListMessage() {
        return list;
    }
}
