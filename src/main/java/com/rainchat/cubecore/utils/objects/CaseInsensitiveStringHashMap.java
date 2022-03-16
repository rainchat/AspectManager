package com.rainchat.cubecore.utils.objects;

import java.util.HashMap;
import java.util.Map;

public class CaseInsensitiveStringHashMap<V> extends CaseInsensitiveStringMap<V> {
    public CaseInsensitiveStringHashMap() {
        super(new HashMap());
    }

    public CaseInsensitiveStringHashMap(Map<? extends String, ? extends V> map) {
        this();
        this.putAll(map);
    }
}
