package com.sonamorningstar.wastelandsurvivor.world.level.tile;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.LivingEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class TrapTile extends TickableTile {
    public TrapTile(String name, boolean walkable) {
        super(name, walkable);
    }

    public TrapTile(String name, String backgroundTile, boolean walkable) {
        super(name, backgroundTile, walkable);
    }

    @Override
    protected void onEntityStep(Level level, Entity entity) {
        if (entity instanceof LivingEntity && Game.INSTANCE.ticks % 60 == 0) {
            ((LivingEntity) entity).hurt(25);
        }
    }
}
