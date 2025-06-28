package com.sonamorningstar.wastelandsurvivor.world.level.tile;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class TeleporterTile extends TickableTile {
    public TeleporterTile(String name, String backgroundTile, boolean walkable) {
        super(name, backgroundTile, walkable);
    }

    @Override
    protected void onEntityStep(Level level, Entity entity) {
        if (entity instanceof Player) {
            Game.INSTANCE.getLevelManager().loadLevel("Desert");
            var nextLevel = Game.INSTANCE.getLevelManager().getCurrentLevel();
            Player player = (Player) entity;
            nextLevel.generateTiles();
            player.changeLevel(nextLevel);
            player.setPosition(new Position(128, 128));
            nextLevel.addEntity(entity);
            nextLevel.bootstrapEntities();
        }
    }
}
