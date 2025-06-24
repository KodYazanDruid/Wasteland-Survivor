package com.sonamorningstar.wastelandsurvivor.object;

import com.sonamorningstar.wastelandsurvivor.registry.AllItems;

public class Item {
    private final String name;
    private final int id;

    public Item(String name) {
        this.name = name;
        this.id = AllItems.nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ItemStack createStack(int count) {
        return new ItemStack(this, count);
    }
}
