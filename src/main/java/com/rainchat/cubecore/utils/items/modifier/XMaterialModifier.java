package com.rainchat.cubecore.utils.items.modifier;

import com.cryptomorin.xseries.XMaterial;
import com.rainchat.cubecore.api.item.ItemModifier;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class XMaterialModifier  implements ItemModifier {
    private String materialString;

    @Override
    public String getName() {
        return "material";
    }

    @Override
    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        XMaterial.matchXMaterial(Chat.translateRaw(materialString, uuid, replacementSource))
                .ifPresent(xMaterial -> xMaterial.setType(original));
        return original;
    }

    @Override
    public Object toObject() {
        return materialString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.materialString = String.valueOf(object);
    }

    @Override
    public void loadFromItemStack(ItemStack itemStack) {
        this.materialString = XMaterial.matchXMaterial(itemStack).name();
    }

    @Override
    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return XMaterial
                .matchXMaterial(Chat.translateRaw(materialString, uuid, replacementSource))
                .map(xMaterial -> xMaterial.isSimilar(itemStack))
                .orElse(false);
    }
}