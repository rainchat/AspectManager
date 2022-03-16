package com.rainchat.cubecore.utils.items.modifier;

import com.rainchat.cubecore.api.item.ItemModifier;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.MathUtil;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NBTModifier implements ItemModifier {
    private String nbtData = "";

    public NBTModifier setNbtData(String nbtData) {
        this.nbtData = nbtData;
        return this;
    }

    @Override
    public String getName() {
        return "nbt";
    }

    @SuppressWarnings("deprecated")
    @Override
    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        if (MathUtil.isNullOrEmpty(nbtData)) {
            return original;
        }
        try {
            return Bukkit.getUnsafe().modifyItemStack(original, Chat.translateRaw(nbtData, uuid, replacementSource));
        } catch (Throwable throwable) {
            return original;
        }
    }

    @Override
    public Object toObject() {
        return nbtData;
    }

    @Override
    public void loadFromObject(Object object) {
        this.nbtData = String.valueOf(object);
    }

    @Override
    public void loadFromItemStack(ItemStack itemStack) {
        // EMPTY
    }

    @Override
    public boolean canLoadFromItemStack(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return false;
    }

}