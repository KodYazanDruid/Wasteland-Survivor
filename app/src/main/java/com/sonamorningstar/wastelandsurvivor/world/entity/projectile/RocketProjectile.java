package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;

public class RocketProjectile extends Projectile {
    public RocketProjectile(Context context) {
        super(ProjectileType.ROCKET, context);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
