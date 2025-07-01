package com.sonamorningstar.wastelandsurvivor.registry;

import com.sonamorningstar.wastelandsurvivor.object.Item;

import java.util.ArrayList;
import java.util.List;

public class AllItems {
    public static final List<Item> ALL_THE_ITEMS = new ArrayList<>();
    public static int nextId = 0;

    public static final Item EMPTY = register(new Item("Empty", new Item.Configs().setStackSize(1)));
    public static final Item APPLE = register(new Item("Apple", new Item.Configs()));
    public static final Item WOOD = register(new Item("Wood", new Item.Configs()));
    public static final Item STONE = register(new Item("Stone", new Item.Configs()));
    public static final Item STEEL_INGOT = register(new Item("Steel Ingot", new Item.Configs()));
    public static final Item SAND_PILE = register(new Item("Sand Pile", new Item.Configs()));
    public static final Item HEALTH_POTION = register(new Item("Health Potion", new Item.Configs().setStackSize(1)));


    private static Item register(Item item) {
        ALL_THE_ITEMS.add(item);
        return item;
    }
}
