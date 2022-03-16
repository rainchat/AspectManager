package com.rainchat.cubecore.gui.menu;

import com.rainchat.cubecore.api.CubeCore;
import com.rainchat.cubecore.gui.Holder;
import com.rainchat.cubecore.gui.InventoryCreator;
import com.rainchat.cubecore.gui.InventoryManager;
import com.rainchat.cubecore.gui.elements.DynamicGuiElement;
import com.rainchat.cubecore.gui.elements.GuiElement;
import com.rainchat.cubecore.gui.elements.GuiElementGroup;
import com.rainchat.cubecore.gui.elements.StaticGuiElement;
import com.rainchat.cubecore.utils.InventoryUtil;
import com.rainchat.cubecore.utils.items.Item;
import com.rainchat.cubecore.utils.objects.CustomFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class SimpleInventory implements Cloneable {

    private final String name;

    private InventoryCreator creator;
    private InventoryManager inventoryManager;

    private Plugin plugin;

    private Inventory inventory;
    private String title;
    private char[] slots;
    private final Map<Character, GuiElement> elements = new HashMap<>();
    private InventoryType inventoryType;
    private int width;

    private Integer pageNumber = 0;
    private Integer pageAmount = 0;

    public SimpleInventory(String name) {
        this.name = name;
    }

    public void setup(Plugin plugin, String title, String[] rows) {
        this.plugin = plugin;
        this.inventoryManager = CubeCore.getAPI().getInventoryAPI().getInventoryManager();
        this.creator = new InventoryCreator(
                (gui, who, type) -> plugin.getServer().createInventory(new Holder(gui), type, gui.getTitle()),
                (gui, who, size) -> plugin.getServer().createInventory(new Holder(gui), size, gui.getTitle()));

        this.title = title;

        width = InventoryUtil.getWidths(rows);
        inventoryType = InventoryUtil.getInventoryType(rows,width);
        slots = InventoryUtil.getCharSlots(rows,width);
    }

    public abstract void setFromConfig(CustomFile config);

    public Plugin getPlugin() {
        return plugin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageNumber(HumanEntity player, int pageNumber) {
        this.pageNumber = pageNumber;
        show(player);
    }

    public int getPageAmount() {
        return pageAmount;
    }

    private void setPageAmount(int pageAmount) {
       this.pageAmount = pageAmount;
    }

    public void setFiller(Item item) {
        addElement(new StaticGuiElement(' ', item));
    }

    public void addElements(GuiElement... elements) {
        for (GuiElement element : elements) {
            addElement(element);
        }
    }

    /**
     * Add an element to the gui
     * @param element   The {@link GuiElement} to add
     */
    public void addElement(GuiElement element) {
        elements.put(element.getSlotChar(), element);
        element.setGui(this);
        element.setSlots(getSlots(element.getSlotChar()));
    }

    /**
     * Set the text of an item using the display name and the lore.
     * Also replaces any placeholders in the text and filters out empty lines.
     * Use a single space to create an emtpy line.
     * @param player    The player viewing the GUI
     * @param item      The {@link ItemStack} to set the text for
     */
    public void setItemText(HumanEntity player, Item item) {
    }

    public GuiElement getElement(int slot) {
        return slot < 0 || slot >= slots.length ? null : elements.get(slots[slot]);
    }

    public GuiElement getFiller() {
        return elements.get(' ');
    }

    private int[] getSlots(char slotChar) {
        ArrayList<Integer> slotList = new ArrayList<>();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slotChar) {
                slotList.add(i);
            }
        }
        return slotList.stream().mapToInt(Integer::intValue).toArray();
    }

    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public InventoryCreator getInventoryCreator() {
        return creator;
    }


    public void updateInventory(HumanEntity who, boolean updateDynamic) {
        if (updateDynamic) {
            updateElements(who, elements.values());
        }
        calculatePageAmount(who);

        if (inventory == null) {
            if (slots.length != inventoryType.getDefaultSize()) {
                inventory = creator.getSizeCreator().create(this, who, slots.length);
            } else {
                inventory = creator.getTypeCreator().create(this, who, inventoryType);
            }
        } else {
            inventory = getInventoryCreator().getTypeCreator().create(this, who, inventoryType);
            inventory.clear();
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            GuiElement element = getElement(i);
            if (element == null) {
                element = getFiller();
            }
            if (element != null) {
                inventory.setItem(i, element.getItem(who, i));
            }
        }
    }

    private void calculatePageAmount(HumanEntity player) {
        int pageAmount = 0;
        for (GuiElement element : elements.values()) {
            int amount = calculateElementSize(player, element);
            if (amount > 0 && (pageAmount - 1) * element.getSlots().length < amount && element.getSlots().length > 0) {
                pageAmount = (int) Math.ceil((double) amount / element.getSlots().length);
            }
        }
        setPageAmount(pageAmount);
        if (getPageNumber() >= pageAmount) {
            setPageNumber(Math.min(0, pageAmount - 1));
        }
    }

    private int calculateElementSize(HumanEntity player, GuiElement element) {
        if (element instanceof GuiElementGroup) {
            return ((GuiElementGroup) element).size();
        } else if (element instanceof DynamicGuiElement) {
            return calculateElementSize(player, ((DynamicGuiElement) element).getCachedElement(player));
        }
        return 0;
    }

    /**
     * Update all dynamic elements in a collection of elements.
     * @param who       The player to update the elements for
     * @param elements  The elements to update
     */
    public void updateElements(HumanEntity who, Collection<GuiElement> elements) {
        for (GuiElement element : elements) {
            if (element instanceof DynamicGuiElement) {
                ((DynamicGuiElement) element).update(who);
            } else if (element instanceof GuiElementGroup) {
                updateElements(who, ((GuiElementGroup) element).getElements());
            }
        }
    }

    /**
     * Show this GUI to a player
     */
    public void show(HumanEntity player) {
        updateInventory(player,true);

        if (player.getOpenInventory().getType() != InventoryType.CRAFTING) {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                Inventory inventory = getInventory();
                player.openInventory(inventory);
            });
        } else {
            Inventory inventory = getInventory();
            player.openInventory(inventory);
        }
        inventoryManager.setPlayerInventory((Player) player,this);
    }

    public void closeInventory() {

    }

    @Override
    public SimpleInventory clone() {
        try {
            SimpleInventory clone = (SimpleInventory) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
