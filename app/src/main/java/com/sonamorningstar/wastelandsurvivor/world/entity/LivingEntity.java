package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Collider;
import com.sonamorningstar.wastelandsurvivor.world.container.Inventory;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public abstract class LivingEntity extends Entity {

    protected float health;
    protected float maxHealth = 100;

    protected Inventory itemInventory;
    protected Inventory charmInventory;

    public LivingEntity(Level level, Collider collider) {
        super(level, collider);
        initInventories();
    }

    public LivingEntity(Level level, Collider collider, double x, double y) {
        super(level, collider, x, y);
        initInventories();
    }

    private void initInventories() {
        // Default sizes, can be overridden by subclasses
        this.itemInventory = new Inventory(27); // 3x9 grid
        this.charmInventory = new Inventory(4);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Inventory getItemInventory() {
        return itemInventory;
    }

    public Inventory getCharmInventory() {
        return charmInventory;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = (float) getPosition().getX();
        float y = (float) getPosition().getY();
        float healthBarWidth = 50;
        float healthBarHeight = 10;
        float healthPercentage = health / maxHealth;
        float healthBarX;
        float healthBarY;
        if (collider instanceof BoundingCircle) {
            BoundingCircle circle = (BoundingCircle) collider;
            healthBarX = x - healthBarWidth / 2;
            healthBarY = (float) (y - circle.getRadius() - healthBarHeight - 5); // 5 pixels above the circle
        } else {
            BoundingBox box = (BoundingBox) collider;
            healthBarX = (float) (x - box.getWidth() / 2);
            healthBarY = (float) (y - box.getHeight() - healthBarHeight - 5); // 5 pixels above the bounding box
        }
        Paint backPaint = new Paint();
        backPaint.setColor(0xFF0F0F0F);
        canvas.drawRoundRect(healthBarX, healthBarY, healthBarX + healthBarWidth, healthBarY + healthBarHeight,
                5, 5, backPaint);
        Paint healthPaint = new Paint();
        healthPaint.setColor(0xFF00FF00); // Green for health
        canvas.drawRoundRect(healthBarX, healthBarY, healthBarX + healthBarWidth * healthPercentage, healthBarY + healthBarHeight,
                5, 5, healthPaint);
    }

    @Override
    public void addedToWorld(Level level) {
        super.addedToWorld(level);
        health = maxHealth;
    }

    public void hurt(float amount) {
        if (!markedForRemoval) {
            health -= amount;
            if (health <= 0) {
                die();
            }
        }
    }

    public void die() {
        markedForRemoval = true;
    }
}
