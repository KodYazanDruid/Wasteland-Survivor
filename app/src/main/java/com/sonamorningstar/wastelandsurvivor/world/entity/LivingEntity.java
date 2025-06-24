package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.world.Collider;

public abstract class LivingEntity extends Entity {
    protected float health;
    protected float maxHealth = 100;

    public LivingEntity(Game game, Context context, Collider collider) {
        super(game, context, collider);
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
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
