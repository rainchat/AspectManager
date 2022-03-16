package com.rainchat.cubecore.utils;

import org.bukkit.event.inventory.InventoryType;

public class InventoryUtil {

    private final static InventoryType[] INVENTORY_TYPES = {
            InventoryType.DISPENSER, // 3*3
            InventoryType.HOPPER, // 5*1
            InventoryType.CHEST // 9*x
    };
    private final static int[] ROW_WIDTHS = {3, 5, 9};


    public static int getWidths(String[] rows) {
        int width = ROW_WIDTHS[0];
        for (String row : rows) {
            if (row.length() > width) {
                width = row.length();
            }
        }
        return width;
    }

    public static InventoryType getInventoryType(String[] rows, int width) {
        InventoryType inventoryType = null;

        for (int i = 0; i < ROW_WIDTHS.length; i++) {
            if (width < ROW_WIDTHS[i]) {
                width = ROW_WIDTHS[i];
            }
            if (width == ROW_WIDTHS[i]) {
                inventoryType = INVENTORY_TYPES[i];
                break;
            }
        }
        return inventoryType;
    }

    public static char[] getCharSlots(String[] rows, int width) {
        char[] slots;

        StringBuilder slotsBuilder = new StringBuilder();

        for (String row : rows) {
            if (row.length() < width) {
                double side = (width - row.length()) / 2;
                for (int i = 0; i < Math.floor(side); i++) {
                    slotsBuilder.append(" ");
                }
                slotsBuilder.append(row);
                for (int i = 0; i < Math.ceil(side); i++) {
                    slotsBuilder.append(" ");
                }
            } else if (row.length() == width) {
                slotsBuilder.append(row);
            } else {
                slotsBuilder.append(row, 0, width);
            }
        }
        slots = slotsBuilder.toString().toCharArray();

        return slots;
    }
}
