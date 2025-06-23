package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;

public abstract class LivingEntity extends Entity {
    protected float health;
    protected float maxHealth;

    public LivingEntity(Context context) {
        super(context);
    }
}
