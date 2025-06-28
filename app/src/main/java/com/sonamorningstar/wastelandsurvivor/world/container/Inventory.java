package com.sonamorningstar.wastelandsurvivor.world.container;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.drawer.ItemDrawer;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<ItemStack> items;
    private final int size;

    public Inventory(int size) {
        this.size = size;
        this.items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.items.add(ItemStack.EMPTY);
        }
    }

    public int getSize() {
        return size;
    }

    public void draw(Canvas canvas, int x, int y) {
        int cellSize = 64; // Size of each inventory cell
        for (int i = 0; i < size; i++) {
            int col = i % 9; // Assuming a 9-column layout
            int row = i / 9;
            int cellX = x + col * (cellSize + 4);
            int cellY = y + row * cellSize;

            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                // Draw the item in the inventory cell
                ItemDrawer.drawItem(canvas, stack, cellX, cellY, 1);
            }
        }
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public ItemStack getItem(int slot) {
        if (slot >= 0 && slot < size) {
            return items.get(slot);
        }
        return ItemStack.EMPTY;
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot >= 0 && slot < size) {
            items.set(slot, stack);
        }
    }

    /**
     * Adds an item to the inventory. It will first try to stack with existing items,
     * then place the remainder in the first available empty slot.
     * The passed ItemStack object is mutated and will contain the remainder.
     *
     * @param stack The ItemStack to add.
     */
    public void addItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        if (stack.isStackable()) {
            for (ItemStack slotStack : items) {
                if (canStack(slotStack, stack)) {
                    mergeStacks(slotStack, stack);
                    if (stack.isEmpty()) {
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, stack.copy());
                stack.setCount(0);
                return;
            }
        }
    }

    private boolean canStack(ItemStack existing, ItemStack toAdd) {
        return !existing.isEmpty() &&
                ItemStack.isSameItem(existing, toAdd) &&
                existing.isStackable() &&
                existing.getCount() < existing.getItem().getConfigs().getStackSize();
    }

    private void mergeStacks(ItemStack existing, ItemStack toAdd) {
        int space = existing.getItem().getConfigs().getStackSize() - existing.getCount();
        int amountToTransfer = Math.min(toAdd.getCount(), space);

        existing.increment(amountToTransfer);
        toAdd.decrement(amountToTransfer);
    }
}