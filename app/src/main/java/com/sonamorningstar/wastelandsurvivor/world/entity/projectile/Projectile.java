package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;

import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;

public abstract class Projectile extends Entity {
    protected final ProjectileType<? extends Projectile> type;
    public Projectile(ProjectileType<? extends Projectile> type, Context context) {
        super(context);
        this.type = type;
    }

    public ProjectileType<? extends Projectile> getType() {
        return type;
    }
}
