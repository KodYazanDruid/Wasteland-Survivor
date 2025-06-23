package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;

public class LaserProjectile extends Projectile {
    public LaserProjectile(Context context) {
        super(ProjectileType.LASER, context);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
