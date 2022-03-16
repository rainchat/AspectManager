package com.rainchat.cubecore.utils.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatickBuilder<T> {

    private final HashMap<String, T> build = new HashMap<>();
    private final HashMap<String, String> nameMap = new HashMap<>();

    public StatickBuilder() {

    }

    public void register(T vClass, String name, String... aliases) {
        this.build.put(name, vClass);
        this.nameMap.put(name, name);


        for (String alias : aliases) {
            this.nameMap.put(alias, name);
        }
    }

    public final List<T> getList() {
        List<T> list = new ArrayList();

        for (Map.Entry<String, T> stringTEntry : this.build.entrySet()) {
            list.add(((Map.Entry<String, T>) (Map.Entry) stringTEntry).getValue());
        }

        return list;
    }

    public final HashMap<String, T> getTypes() {
        return build;
    }

    public T build(String name) {
        return build.get(name);
    }

    public String getName(String name) {
        return this.nameMap.get(name);
    }


}
