package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;

public class RocketProjectile extends Projectile {
    public RocketProjectile(Entity owner, Game game, Context context) {
        super(ProjectileType.ROCKET, game, owner, context, new BoundingBox(0, 0, 40, 20));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
