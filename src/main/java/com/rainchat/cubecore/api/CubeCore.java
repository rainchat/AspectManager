package com.rainchat.cubecore.api;

import org.bukkit.plugin.Plugin;

public class CubeCore {

    private static CubeApi api = null;

    private CubeCore() {
    }

    public static void setAPI(CubeApi api) {
        if (CubeCore.api != null)
            throw new UnsupportedOperationException("Cannot redefine API singleton");

        if (api == null)
            throw new NullPointerException("API cannot be null");

        CubeCore.api = api;
    }

    public static Plugin getPlugin() {
        return api.getPlugin();
    }

    public static CubeApi getAPI() {
        return api;
    }

}
