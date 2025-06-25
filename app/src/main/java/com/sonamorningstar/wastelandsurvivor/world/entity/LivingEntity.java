package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Collider;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class LivingEntity extends Entity {
    protected float health;
    protected float maxHealth = 100;

    protected final List<ItemStack> items = new CopyOnWriteArrayList<>();
    protected final List<ItemStack> charms = new CopyOnWriteArrayList<>();

    public LivingEntity(Game game, Context context, Collider collider) {
        super(game, context, collider);
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
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
    public void addedToWorld(Game game) {
        super.addedToWorld(game);
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
