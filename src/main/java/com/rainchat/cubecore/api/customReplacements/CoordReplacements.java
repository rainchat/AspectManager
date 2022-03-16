package com.rainchat.cubecore.api.customReplacements;

import com.rainchat.cubecore.utils.placeholder.CustomPlaceholder;
import org.bukkit.Location;

public class CoordReplacements extends CustomPlaceholder<Location> {

    private final Location location;


    public CoordReplacements(Location location) {
        super("location_");

        this.location = location;
    }


    @Override
    public Class<Location> forClass() {
        return Location.class;
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        return switch (base) {
            case "x" -> String.valueOf(location.getX());
            case "y" -> String.valueOf(location.getY());
            case "z" -> String.valueOf(location.getZ());
            case "world" -> location.getWorld().getName();
            default -> "";
        };

    }

}
