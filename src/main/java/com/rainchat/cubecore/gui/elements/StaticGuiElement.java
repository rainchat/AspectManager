package com.rainchat.cubecore.gui.elements;

/*
 * Copyright 2017 Max Lee (https://github.com/Phoenix616)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.rainchat.cubecore.utils.items.Item;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a simple element in a gui to which an action can be assigned.
 * If you want the item to change on click you have to do that yourself.
 */
public class StaticGuiElement extends GuiElement {
    private Item item;


    public StaticGuiElement(int[] slots, Item item, Action action) throws IllegalArgumentException {
        super(slots, action);
        this.item = item;
    }
    
    /**
     * Represents an element in a gui
     * @param slotChar  The character to replace in the gui setup string
     * @param item      The item this element displays
     * @param number    The number, 1 will not display the number
     * @param action    The action to run when the player clicks on this element
     * @throws IllegalArgumentException If the number is below 1 or above the max stack count (currently 64)
     */
    public StaticGuiElement(char slotChar, Item item, int number, Action action) throws IllegalArgumentException {
        super(slotChar, action);
        this.item = item;
    }
    
    /**
     * Represents an element in a gui
     * @param slotChar  The character to replace in the gui setup string
     * @param item      The item this element displays
     * @param action    The action to run when the player clicks on this element
     */
    public StaticGuiElement(char slotChar, Item item, Action action) {
        this(slotChar, item, item != null ? item.getAmount() : 1, action);
    }

    /**
     * Represents an element in a gui that doesn't have any action when clicked
     * @param slotChar  The character to replace in the gui setup string
     * @param item      The item this element displays
     */
    public StaticGuiElement(char slotChar, Item item) {
        this(slotChar, item, item != null ? item.getAmount() : 1, null);
    }


    /**
     * Set the item that is displayed by this element
     * @param item  The item that should be displayed by this element
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Get the raw item displayed by this element which was passed to the constructor or set with {@link #setItem(Item)}.
     * This item will not have the amount or text applied! Use {@link #getItem(HumanEntity, int)} for that!
     * @return  The raw item
     */
    public Item getRawItem() {
        return item;
    }

    @Override
    public ItemStack getItem(HumanEntity who, int slot) {
        if (item == null) {
            return null;
        }
        ItemStack clone = item.build();

        return clone;
    }

    public boolean setNumber(int number) {
        if (number < 1 || number > 64) {
            item.amount(1);
            return false;
        }
        item.amount(number);
        return true;
    }

}
