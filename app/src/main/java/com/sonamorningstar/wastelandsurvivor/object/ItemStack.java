package com.sonamorningstar.wastelandsurvivor.object;

public class ItemStack {
    private final Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
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
}
