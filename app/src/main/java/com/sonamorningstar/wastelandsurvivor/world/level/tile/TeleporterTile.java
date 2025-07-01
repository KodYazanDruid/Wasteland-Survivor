package com.sonamorningstar.wastelandsurvivor.world.level.tile;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class TeleporterTile extends TickableTile {

    private String destinationLevel = "Highway";
    public TeleporterTile(String name, String backgroundTile, boolean walkable) {
        super(name, backgroundTile, walkable);
    }

    public TeleporterTile(String name, String backgroundTile, boolean walkable, String destinationLevel) {
        super(name, backgroundTile, walkable);
        this.destinationLevel = destinationLevel;
    }

    public void setDestinationLevel(String destinationLevel) {
        this.destinationLevel = destinationLevel;
    }

    @Override
    protected void onEntityStep(Level level, Entity entity) {
        if (entity instanceof Player) {
            Game.INSTANCE.getLevelManager().loadLevel(destinationLevel);
            var nextLevel = Game.INSTANCE.getLevelManager().getCurrentLevel();
            Player player = (Player) entity;
            player.getLevel().removeEntity(player);
            player.changeLevel(nextLevel);
            player.setPosition(new Position(x, y));
            Game.INSTANCE.joystick.resetActuator();
            Game.INSTANCE.joystick.setPressed(false);
            nextLevel.addEntity(entity);
        }
    }
}
