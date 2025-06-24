package com.sonamorningstar.wastelandsurvivor.registry;

import com.sonamorningstar.wastelandsurvivor.object.Item;

import java.util.ArrayList;
import java.util.List;

public class AllItems {
    public static final List<Item> ALL_THE_ITEMS = new ArrayList<>();
    public static int nextId = 0;

    public static final Item APPLE = register(new Item("Apple"));


    private static Item register(Item item) {
        ALL_THE_ITEMS.add(item);
        return item;
    }
}
