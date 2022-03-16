package com.rainchat.cubecore.utils.general;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class BukkitUtils {


    public static int getPing(@NotNull final Player player) {
        Object entityPlayer;
        int ping = -9;
        try {
            entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
        } catch (Exception e) {
            Bukkit.getServer().getLogger().log(Level.WARNING, "Unexpected error when getting handle", e);
            return ping;
        }
        try {
            ping = entityPlayer.getClass().getField("ping").getInt(entityPlayer);
        } catch (Exception e) {
            try {
                ping = entityPlayer.getClass().getField("e").getInt(entityPlayer);
            } catch (Exception e1) {
                Bukkit.getServer().getLogger().log(Level.WARNING, "Unexpected error when getting ping", e1);
            }
        }
        return ping;
    }


    /**
     * Get all unique ids
     *
     * @return the unique ids
     */
    @NotNull
    public static List<UUID> getAllUniqueIds() {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                .parallel()
                .map(OfflinePlayer::getUniqueId)
                .collect(Collectors.toList());
    }

    /**
     * Get all player names
     *
     * @return the player names
     */
    @NotNull
    public static List<String> getAllPlayerNames() {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                .parallel()
                .map(OfflinePlayer::getName)
                .collect(Collectors.toList());
    }

    /**
     * Get missing plugins from a list of given plugins
     *
     * @param depends the list of plugins
     *
     * @return the missing plugins
     */
    @NotNull
    public static List<String> getMissingDepends(@NotNull final List<String> depends) {
        return depends.parallelStream()
                .filter(depend -> Bukkit.getPluginManager().getPlugin(depend) == null)
                .collect(Collectors.toList());
    }

    public static boolean isUsername(@NotNull String string, List<Character> allowedCharacters) {
        int len = string.length();
        if (len < 3 || len > 16) return false;

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (!Character.isLetterOrDigit(ch) && !allowedCharacters.contains(ch)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isUsername(@NotNull String string) {
        return isUsername(string, Arrays.asList('_', '-', '.'));
    }
}