package com.rainchat.cubecore.utils.items.modifier;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.rainchat.cubecore.api.item.ItemMetaModifier;
import com.rainchat.cubecore.utils.general.BukkitUtils;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.MathUtil;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SkullModifier extends ItemMetaModifier {
    private static final boolean PLAYER_PROFILE_SUPPORT;

    static {
        PLAYER_PROFILE_SUPPORT = MathUtil.isMethodLoaded(Player.class.getName(), "getPlayerProfile");
    }

    private String skullString = "";

    @Override
    public String getName() {
        return "skull";
    }

    @Override
    public ItemMeta modifyMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        if (!(meta instanceof SkullMeta)) {
            return meta;
        }
        String value = Chat.translateRaw(skullString, uuid, replacementSource);
        if (!BukkitUtils.isUsername(value)) {
            return SkullUtils.applySkin(meta, value);
        }
        Player player = Bukkit.getPlayer(value);
        if (player != null) {
            if (!PLAYER_PROFILE_SUPPORT || XMaterial.getVersion() < 12) {
                return SkullUtils.applySkin(meta, player);
            }
            if (player.getPlayerProfile().hasTextures()) {
                ((SkullMeta) meta).setPlayerProfile(player.getPlayerProfile());
            }
        }

        return meta;
    }

    @Override
    public void loadFromItemMeta(ItemMeta meta) {
        this.skullString = Optional.ofNullable(SkullUtils.getSkinValue(meta)).orElse("");
    }

    @Override
    public boolean canLoadFromItemMeta(ItemMeta meta) {
        return meta instanceof SkullMeta;
    }

    @Override
    public boolean compareWithItemMeta(ItemMeta meta, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return false;
    }

    @Override
    public Object toObject() {
        return skullString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.skullString = String.valueOf(object);
    }
}
