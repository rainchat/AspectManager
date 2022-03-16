package com.rainchat.cubecore.api.customReplacements;


import com.rainchat.cubecore.utils.placeholder.CustomPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TargetPlayerReplacements extends CustomPlaceholder<Player> {
    private final OfflinePlayer player;


    public TargetPlayerReplacements(OfflinePlayer player) {
        super("player_");

        this.player = player;
    }

    public TargetPlayerReplacements(UUID playerUUID) {
        super("player_");

        this.player = Bukkit.getOfflinePlayer(playerUUID);
    }

    @Override
    public Class<Player> forClass() {
        return Player.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {


        switch (base) {
            case "name":
                return player.getName();

            case "world":
                if (player instanceof Player) {
                    return ((Player) player).getWorld().getName();
                }

                return "";
            case "gamemode":
                return ((Player) player).getGameMode().toString();

            case "uuid":
                return player.getUniqueId().toString();
        }

        return "";
    }

}
