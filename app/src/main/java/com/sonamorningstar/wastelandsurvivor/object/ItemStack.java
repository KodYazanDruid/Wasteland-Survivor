package com.sonamorningstar.wastelandsurvivor.object;

import com.sonamorningstar.wastelandsurvivor.registry.AllItems;

public class ItemStack {
    private static final int MAX_STACK_SIZE = 64; // Maximum number of items in a stack
    public static final ItemStack EMPTY = new ItemStack(AllItems.EMPTY, 0);
    private final Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public static boolean isSameItem(ItemStack existing, ItemStack toAdd) {
        return existing.getItem() == toAdd.getItem();
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment(int amount) {
        this.count += amount;
        if (this.count > MAX_STACK_SIZE) {
            this.count = MAX_STACK_SIZE; // Cap the stack size
        }
    }

    public void decrement(int amount) {
        this.count -= amount;
        if (this.count < 0) {
            this.count = 0;
        }
    }

    public boolean isEmpty() {
        return count <= 0;
    }

    public boolean isStackable() {
        return item.getConfigs().getStackSize() > 1 && count < item.getConfigs().getStackSize();
    }

    public ItemStack copy() {
        return new ItemStack(item, count);
    }
}
