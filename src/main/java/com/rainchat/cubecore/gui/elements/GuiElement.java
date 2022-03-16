package com.rainchat.cubecore.gui.elements;

import com.rainchat.cubecore.gui.menu.SimpleInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an element in a gui
 */
public abstract class GuiElement {
    private char slotChar;
    private Action action;
    protected int[] slots = new int[0];
    protected SimpleInventory gui;

    /**
     * Represents an element in a gui
     * @param slotChar The character to replace in the gui setup string
     * @param action   The action to run when the player clicks on this element
     */
    public GuiElement(char slotChar, Action action) {
        this.slotChar = slotChar;
        setAction(action);
    }

    public GuiElement(int[] slots, Action action) {
        this.slots = slots;
        setAction(action);
    }

    /**
     * Represents an element in a gui that doesn't have any action when clicked
     * @param slotChar  The character to replace in the gui setup string
     */
    public GuiElement(char slotChar) {
        this(slotChar, null);
    }

    /**
     * Get the character in the gui setup that corresponds with this element
     * @return  The character
     */
    public char getSlotChar() {
        return slotChar;
    }

    /**
     * Get the item that is displayed by this element on a certain page
     * @param who   The player who views the page
     * @param slot  The slot to get the item for
     * @return      The ItemStack that is displayed as this element
     */
    public abstract ItemStack getItem(HumanEntity who, int slot);

    /**
     * Get the action that is executed when clicking on this element
     * @param who   The player who views the page
     * @return      The action to run
     */
    public Action getAction(HumanEntity who) {
        return action;
    }

    /**
     * Set the action that is executed when clicking on this element
     * @param action    The action to run. The {@link Action#onClick} method should
     *                  return whether or not the click event should be cancelled
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Get the indexes of the lots that this element is displayed in
     * @return An array of the lost indexes
     */
    public int[] getSlots() {
        return slots;
    }

    /**
     * Set the ids of the slots where this element is assigned to
     * @param slots An array of the slot ids where this element is displayed
     */
    public void setSlots(int[] slots) {
        this.slots = slots;
    }

    /**
     * Get the index that this slot has in the list of slots that this element is displayed in
     * @param slot  The id of the slot
     * @return      The index in the list of slots that this id has or <code>-1</code> if it isn't in that list
     */
    public int getSlotIndex(int slot) {
        return getSlotIndex(slot, 0);
    }

    /**
     * Get the index that this slot has in the list of slots that this element is displayed in
     * @param slot          The id of the slot
     * @param pageNumber    The number of the page that the gui is on
     * @return              The index in the list of slots that this id has or <code>-1</code> if it isn't in that list
     */
    public int getSlotIndex(int slot, int pageNumber) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slot) {
                return i + slots.length * pageNumber;
            }
        }
        return -1;
    }

    /**
     * Set the gui this element belongs to
     * @param gui   The GUI that this element is in
     */
    public void setGui(SimpleInventory gui) {
        this.gui = gui;
    }

    public static interface Action {

        /**
         * Executed when a player clicks on an element
         * @param click The Click class containing information about the click
         * @return Whether or not the click event should be cancelled
         */
        boolean onClick(Click click);

    }

    public static class Click {
        private final SimpleInventory gui;
        private final int slot;
        private final GuiElement element;
        private final InventoryClickEvent event;

        public Click(SimpleInventory gui, int slot, GuiElement element, InventoryClickEvent event) {
            this.gui = gui;
            this.slot = slot;
            this.element = element;
            this.event = event;
        }

        /**
         * Get the slot of the GUI that was clicked
         * @return  The clicked slot
         */
        public int getSlot() {
            return slot;
        }

        /**
         * Get the element that was clicked
         * @return  The clicked GuiElement
         */
        public GuiElement getElement() {
            return element;
        }

        /**
         * Get the type of the click
         * @return  The type of the click
         */
        public ClickType getType() {
            return event.getClick();
        }

        /**
         * Get who clicked the element
         * @return  The player that clicked
         */
        public HumanEntity getWhoClicked() {
            return event.getWhoClicked();
        }

        /**
         * Get the event of the click
         * @return  The InventoryClickEvent associated with this Click
         */
        public InventoryClickEvent getEvent() {
            return event;
        }

        public SimpleInventory getGui() {
            return gui;
        }
    }
}
