package com.rainchat.cubecore.utils.items.modifier;

import com.rainchat.cubecore.api.item.ItemModifier;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.MathUtil;
import com.rainchat.cubecore.utils.placeholder.PlaceholderSupply;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AmountModifier implements ItemModifier {
    private String amount = "1";

    public AmountModifier() {
    }

    public String getName() {
        return "amount";
    }

    public ItemStack modify(ItemStack original, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        MathUtil.getNumber(Chat.translateRaw(this.amount, uuid, replacementSource));
        return original;
    }

    public Object toObject() {
        return this.amount;
    }

    public void loadFromObject(Object object) {
        this.amount = String.valueOf(object);
    }

    public void loadFromItemStack(ItemStack itemStack) {
        this.amount = String.valueOf(itemStack.getAmount());
    }

    public boolean compareWithItemStack(ItemStack itemStack, UUID uuid, List<PlaceholderSupply<?>> replacementSource) {
        return (Boolean)MathUtil.getNumber(Chat.translateRaw(this.amount, uuid, replacementSource)).map((bigDecimal) -> {
            return bigDecimal.intValue() >= itemStack.getAmount();
        }).orElse(false);
    }

    public AmountModifier setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public AmountModifier setAmount(int amount) {
        this.amount = String.valueOf(amount);
        return this;
    }
}
