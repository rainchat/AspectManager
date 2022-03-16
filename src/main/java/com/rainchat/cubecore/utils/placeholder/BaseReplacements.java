package com.rainchat.cubecore.utils.placeholder;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class BaseReplacements {

    public Player player;
    public String[] options;

    @NotNull
    public abstract String getIdentifier();

    @NotNull
    public String getName() {
        return this.getIdentifier();
    }

    public void setup(Player player,String... options) {
        this.options = options;
        this.player = player;
    }


    public final String getReplacement(String forKey) {
        return getPrefixedReplacement(getName(), forKey);
    }

    final String getPrefixedReplacement(String prefix, String forKey) {

        if (forKey.startsWith(prefix)) {
            forKey = forKey.substring(prefix.length());
        }

        int split = forKey.indexOf('.');

        if (split < 0) {
            return getReplacement(forKey, forKey);
        }

        return getReplacement(forKey.substring(0, split), forKey);
    }

    protected abstract String getReplacement(String base, String fullKey);


}
