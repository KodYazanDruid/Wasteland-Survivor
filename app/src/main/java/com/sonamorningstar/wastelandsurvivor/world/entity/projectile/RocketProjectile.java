package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class RocketProjectile extends Projectile {
    public RocketProjectile(Entity owner, Level level) {
        super(ProjectileType.ROCKET, level, owner, new BoundingBox(0, 0, 40, 20));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
