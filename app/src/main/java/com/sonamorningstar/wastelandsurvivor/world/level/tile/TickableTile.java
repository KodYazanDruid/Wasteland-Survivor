package com.sonamorningstar.wastelandsurvivor.world.level.tile;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.LivingEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public abstract class TickableTile extends Tile {
    public TickableTile(String name, boolean walkable) {
        super(name, walkable);
    }

    public TickableTile(String name, String backgroundTile, boolean walkable) {
        super(name, backgroundTile, walkable);
    }

    public void tick(Level level) {
        var entities = level.getEntitiesAboveTile(this);
        for (Entity entity : entities) {
            onEntityStep(level, entity);
        }
    }

    protected void onEntityStep(Level level, Entity entity) {

    }
}
