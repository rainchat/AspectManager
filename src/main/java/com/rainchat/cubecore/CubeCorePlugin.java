package com.rainchat.cubecore;

import com.rainchat.cubecore.api.CubeApi;
import com.rainchat.cubecore.api.CubeCore;
import org.bukkit.plugin.java.JavaPlugin;

public final class CubeCorePlugin extends JavaPlugin {

    private CubeImpl api;
    private static CubeCorePlugin instance;


    @Override
    public void onEnable() {

        instance = this;
        api = new CubeImpl(this);
        api.load();
    }

    @Override
    public void onDisable() {

    }

    public static CubeCorePlugin getInstance() {
        return instance;
    }

    public static CubeApi getAPI() {
        return CubeCore.getAPI();
    }
}
