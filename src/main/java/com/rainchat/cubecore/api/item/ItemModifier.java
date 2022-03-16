package com.rainchat.cubecore.api.item;

import com.rainchat.cubecore.utils.placeholder.CustomPlaceholder;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ItemModifier {
    String getName();

    ItemStack modify(ItemStack var1, UUID var2, List<PlaceholderSupply<?>> replacementSource);

    Object toObject();

    void loadFromObject(Object var1);

    void loadFromItemStack(ItemStack var1);

    default boolean canLoadFromItemStack(ItemStack itemStack) {
        return true;
    }

    boolean compareWithItemStack(ItemStack var1, UUID var2, List<PlaceholderSupply<?>> replacementSource);

    default boolean compareWithItemStack(ItemStack itemStack, UUID uuid) {
        return this.compareWithItemStack(itemStack, uuid, Collections.emptyList());
    }

    default boolean compareWithItemStack(ItemStack itemStack) {
        return this.compareWithItemStack(itemStack, UUID.randomUUID(), Collections.emptyList());
    }

    default ItemStack modify(ItemStack original, UUID uuid) {
        return this.modify(original, uuid, Collections.emptyList());
    }

    default ItemStack modify(ItemStack original, UUID uuid, ItemBuilder itemBuilder) {
        return this.modify(original, uuid, itemBuilder.getStringReplacerMap());
    }
}