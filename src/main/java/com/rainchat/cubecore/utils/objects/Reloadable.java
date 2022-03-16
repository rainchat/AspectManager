package com.rainchat.cubecore.utils.objects;

import com.rainchat.cubecore.utils.loader.Control;

public interface Reloadable {
    void onDiscard();

    void onSave();

    void onReload();

    @Control
    void onEnable();
}
