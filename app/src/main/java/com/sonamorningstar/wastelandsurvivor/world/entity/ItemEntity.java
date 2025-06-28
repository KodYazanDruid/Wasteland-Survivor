package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.drawer.ItemDrawer;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class ItemEntity extends Entity {
    private final ItemStack itemStack;

    public ItemEntity(ItemStack stack, Level level, double x, double y) {
        super(level, new BoundingBox(x, y, 64, 64), x, y);
        this.itemStack = stack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        ItemDrawer.drawItem(canvas, itemStack, getPosition().getX(), getPosition().getY(), 1);
    }
}
