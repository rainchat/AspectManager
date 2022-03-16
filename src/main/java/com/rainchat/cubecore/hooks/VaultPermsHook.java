package com.rainchat.cubecore.hooks;

import com.google.common.primitives.Ints;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultPermsHook {

    private static Permission perms;
    private static Chat chat;

    public static boolean setup() {
        final RegisteredServiceProvider<Permission> permsProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        if (permsProvider != null) {
            perms = permsProvider.getProvider();
        }

        final RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return perms != null && chat != null;
    }

    public static String getWorldName() {
        return getMainWorld().getName();
    }

    public static World getMainWorld() {
        return Bukkit.getWorlds().get(0);
    }

    public static String[] getGroups(OfflinePlayer p) {
        final String[] groups = perms.getPlayerGroups(getWorldName(), p);
        return groups == null ? new String[]{} : groups;
    }

    public static String getMainGroup(OfflinePlayer p) {
        final String group = perms.getPrimaryGroup(getWorldName(), p);
        return group == null ? "" : group;
    }

    public static boolean hasPermission(OfflinePlayer p, String perm) {
        return perms.playerHas(getWorldName(), p, perm);
    }

    private static String getPlayerPrefix(OfflinePlayer p) {
        final String prefix = chat.getPlayerPrefix(getWorldName(), p);
        return prefix == null ? "" : prefix;
    }

    private static String getPlayerSuffix(OfflinePlayer p) {
        final String suffix = chat.getPlayerSuffix(getWorldName(), p);
        return suffix == null ? "" : suffix;
    }

    private String getGroupSuffix(OfflinePlayer p) {
        final String suffix = chat.getGroupSuffix(getMainWorld(), getMainGroup(p));
        return suffix == null? "" : suffix;
    }

    private String getGroupPrefix(OfflinePlayer p) {
        final String prefix = chat.getGroupPrefix(getMainWorld(), getMainGroup(p));
        return prefix == null ? "" : prefix;
    }

    private String getGroupSuffix(OfflinePlayer p, int n) {
        final String[] groups = getGroups(p);

        if (n > groups.length) {
            return "";
        }

        for (int i = n - 1; i < groups.length; i++) {
            final String suffix = chat.getGroupSuffix(getMainWorld(), groups[i]);

            if (suffix != null) {
                return suffix;
            }
        }

        return "";
    }

    private String getGroupPrefix(OfflinePlayer p, int n) {
        final String[] groups = getGroups(p);

        if (n > groups.length) {
            return "";
        }

        for (int i = n - 1; i < groups.length; i++) {
            final String prefix = chat.getGroupPrefix(getMainWorld(), groups[i]);

            if (prefix != null) {
                return prefix;
            }
        }

        return "";
    }

    private String getLastColor(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int c = s.lastIndexOf(ChatColor.COLOR_CHAR);
        if (c == -1) {
            c = s.lastIndexOf("&");
            if (c == -1) {
                return "";
            }
        }
        String clr = s.substring(c);
        if (c - 2 >= 0) {
            if (s.charAt(c - 2) == ChatColor.COLOR_CHAR || s.charAt(c - 2) == '&') {
                clr = s.substring(c - 2);
            }
        }
        return clr;
    }

    private int parseInt(String string, int def) {
        final Integer integer = Ints.tryParse(string);
        return integer == null ? def : integer;
    }

    private String bool(boolean bool) {
        return bool ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
    }

    public boolean hasPerm(OfflinePlayer p, String perm) {
        if (perms != null) {
            return perms.playerHas(getWorldName(), p, perm);
        }
        return p.isOnline() && ((Player) p).hasPermission(perm);
    }

    public String[] getServerGroups() {
        final String[] groups = perms.getGroups();
        return groups == null ? new String[]{} : groups;
    }



}
