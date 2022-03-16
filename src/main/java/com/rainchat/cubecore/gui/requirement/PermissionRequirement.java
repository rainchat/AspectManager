package com.rainchat.cubecore.gui.requirement;

import com.rainchat.cubecore.api.requirement.BaseRequirement;
import com.rainchat.cubecore.utils.objects.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PermissionRequirement extends BaseRequirement<List<String>> {

    public PermissionRequirement(String name) {
        super(name);
    }

    @Override
    public List<String> getParsedValue(UUID uuid) {
        return CollectionUtils.createStringListFromObject(value, true);
    }

    @Override
    public boolean check(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return true;
        }
        return getParsedValue(uuid).parallelStream().allMatch(s -> hasPermission(player, s));
    }

    private boolean hasPermission(Player player, String permission) {
        if (permission.startsWith("-")) {
            return !player.hasPermission(permission.substring(1).trim());
        } else {
            return player.hasPermission(permission);
        }
    }

    @Override
    public void take(UUID uuid) {
        // EMPTY
    }
}
