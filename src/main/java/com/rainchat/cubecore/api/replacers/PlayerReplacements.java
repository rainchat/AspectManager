package com.rainchat.cubecore.api.replacers;


import com.rainchat.cubecore.utils.placeholder.BaseReplacements;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerReplacements extends BaseReplacements {

    @Override
    public @NotNull String getIdentifier() {
        return "player_";
    }

    @Override
    public String getReplacement(String base, String fullKey) {


        switch (base) {
            case "name":
                return player.getName();

            case "world":
                if (player != null) {
                    return ((Player) player).getWorld().getName();
                }

                return "";
            case "gamemode":
                return player.getGameMode().toString();

            case "uuid":
                return player.getUniqueId().toString();
        }

        return "";
    }

}
