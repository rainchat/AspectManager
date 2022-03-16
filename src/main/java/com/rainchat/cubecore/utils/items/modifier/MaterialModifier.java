package com.rainchat.cubecore.utils.items.modifier;

import com.cryptomorin.xseries.XEnchantment;
import com.rainchat.cubecore.api.item.ItemMetaModifier;
import com.rainchat.cubecore.api.item.ItemModifier;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.MathUtil;
import com.rainchat.cubecore.utils.objects.CollectionUtils;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class MaterialModifier implements ItemModifier {
    private String materialString;

    @Override
    public String getName() {
        return "material";
    }

    @Override
    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        Optional
                .ofNullable(Material.matchMaterial(Chat.translateRaw(materialString, uuid, replacementSource)))
                .ifPresent(original::setType);
        return original;
    }

    @Override
    public Object toObject() {
        return this.materialString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.materialString = String.valueOf(object);
    }

    @Override
    public void loadFromItemStack(ItemStack itemStack) {
        this.materialString = itemStack.getType().name();
    }

    @Override
    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return itemStack.getType().name().equalsIgnoreCase(Chat.translateRaw(materialString, uuid, replacementSource));
    }

    /**
     * Set the material
     *
     * @param material the material
     *
     * @return {@code this} for builder chain
     */
    public MaterialModifier setMaterial(Material material) {
        this.materialString = material.name();
        return this;
    }

    /**
     * Set the material
     *
     * @param material the material
     *
     * @return {@code this} for builder chain
     */
    public MaterialModifier setMaterial(String material) {
        this.materialString = material;
        return this;
    }
}