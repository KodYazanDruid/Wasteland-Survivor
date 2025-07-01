package com.sonamorningstar.wastelandsurvivor.world.level.levels;

import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.registry.AllItems;
import com.sonamorningstar.wastelandsurvivor.world.entity.Enemy;
import com.sonamorningstar.wastelandsurvivor.world.entity.ItemEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TeleporterTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class ForestLevel extends Level {
    public ForestLevel(String name, int width, int height, String seed) {
        super(name, width, height, seed);
    }

    @Override
    public void generateTiles() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Tile tile = i == 0 || j == 0 || i == getHeight() - 1 || j == getWidth() - 1 ?
                        new Tile("green_tree", "grass", false) : new Tile("grass", true);
                tile.setPosition(j * getTileSize(), i * getTileSize());
                getTiles()[i][j] = tile;
            }
        }

        var teleporter = new TeleporterTile("yellow_tree", "grass", true, "Highway");
        teleporter.setPosition(3 * 128, 3 * 128);
        getTiles()[3][3] = teleporter;
        getTickableTiles().add(teleporter);
    }

    @Override
    public void bootstrapEntities() {
        // Spawn items and enemies in random, walkable locations
        spawnItemOnWalkableTile(AllItems.APPLE.createStack(1));
        spawnItemOnWalkableTile(AllItems.APPLE.createStack(1));
        spawnItemOnWalkableTile(AllItems.HEALTH_POTION.createStack(1));

        // Spawn a few enemies
        for (int i = 0; i < 3; i++) {
            spawnEnemyOnWalkableTile();
        }
    }

    @Override
    public void spawnItemOnWalkableTile(ItemStack stack) {
        int x, y;
        do {
            x = random.nextInt(getHeight());
            y = random.nextInt(getWidth());
        } while (!getTiles()[y][x].isWalkable());

        int pixelX = x * getTileSize() + getTileSize() / 2;
        int pixelY = y * getTileSize() + getTileSize() / 2;
        addEntity(new ItemEntity(stack, this, pixelX, pixelY));
    }

    private void spawnEnemyOnWalkableTile() {
        int x, y;
        do {
            x = random.nextInt(getHeight());
            y = random.nextInt(getWidth());
        } while (!getTiles()[y][x].isWalkable());

        int pixelX = x * getTileSize() + getTileSize() / 2;
        int pixelY = y * getTileSize() + getTileSize() / 2;
        Enemy enemy = new Enemy(this, pixelX, pixelY);
        addEntity(enemy);
    }
}
