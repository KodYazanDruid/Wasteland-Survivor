package com.sonamorningstar.wastelandsurvivor.object;

import com.sonamorningstar.wastelandsurvivor.registry.AllItems;

public class Item {
    private final String name;
    private final Configs configs;
    private final int id;

    public Item(String name, Configs configs) {
        this.name = name;
        this.configs = configs;
        this.id = AllItems.nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Configs getConfigs() {
        return configs;
    }

    public ItemStack createStack(int count) {
        return new ItemStack(this, count);
    }

    public static class Configs {
        private int stackSize = 64;
        private Rarity rarity = Rarity.COMMON;

        public Configs() {}

        public int getStackSize() {
            return stackSize;
        }

        public Rarity getRarity() {
            return rarity;
        }

        public Configs setStackSize(int stackSize) {
            this.stackSize = stackSize;
            return this;
        }

        public Configs setRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }
    }

    public enum Rarity {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY,
        MYTHIC,

        QUEST;
    }
}
