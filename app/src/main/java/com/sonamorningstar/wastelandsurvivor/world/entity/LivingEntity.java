package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;

public abstract class LivingEntity extends Entity {
    protected float health;
    protected float maxHealth = 100;

    public LivingEntity(Context context) {
        super(context);
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void addedToWorld() {
        super.addedToWorld();
        health = maxHealth;
    }
}
