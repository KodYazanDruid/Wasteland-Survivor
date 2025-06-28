package com.sonamorningstar.wastelandsurvivor.world.level.levels;

import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.registry.AllItems;
import com.sonamorningstar.wastelandsurvivor.world.entity.Enemy;
import com.sonamorningstar.wastelandsurvivor.world.entity.ItemEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TeleporterTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TickableTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class ForestLevel extends Level {
    public ForestLevel(String name, int xLen, int yLen, String seed) {
        super(name, xLen, yLen, seed);
    }

    @Override
    public void generateTiles() {
        // Base fill with grass
        for (int i = 0; i < getXLen(); i++) {
            for (int j = 0; j < getYLen(); j++) {
                getTiles()[i][j] = new Tile("grass", true);
            }
        }

        for (int i = 0; i < getXLen(); i++) {
            for (int j = 0; j < getYLen(); j++) {
                if (i == 0 || j == 0 || i == getXLen() - 1 || j == getYLen() - 1) {
                    getTiles()[i][j] = new Tile("green_tree", "grass", false);
                }
            }
        }

        for (int i = 1; i < getXLen() - 1; i++) {
            for (int j = 1; j < getYLen() - 1; j++) {
                double noise = random.nextDouble();
                if (noise < 0.2) {
                    getTiles()[i][j] = new Tile("dirt", "grass", true);
                } else if (noise > 0.9) {
                    getTiles()[i][j] = new Tile("green_tree", "grass", false);
                } else if (noise > 0.98) {
                    getTiles()[i][j] = new Tile("rock_mossy", "grass", false);
                }
            }
        }

        int clearX = 5;
        int clearY = 5;
        for (int i = clearX - 1; i <= clearX + 1; i++) {
            for (int j = clearY - 1; j <= clearY + 1; j++) {
                if (i > 0 && i < getXLen() - 1 && j > 0 && j < getYLen() - 1) {
                    getTiles()[i][j] = new Tile("grass", true);
                }
            }
        }

        getTiles()[clearX][clearY] = new TeleporterTile("yellow_tree", "grass", true);

        for (int i = 0; i < getXLen(); i++) {
            for (int j = 0; j < getYLen(); j++) {
                getTiles()[i][j].setPosition(j * getTileSize(), i * getTileSize());
                if (getTiles()[i][j] instanceof TickableTile) {
                    getTickableTiles().add(((TickableTile) getTiles()[i][j]));
                }
            }
        }
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
            x = random.nextInt(getYLen());
            y = random.nextInt(getXLen());
        } while (!getTiles()[y][x].isWalkable());

        int pixelX = x * getTileSize() + getTileSize() / 2;
        int pixelY = y * getTileSize() + getTileSize() / 2;
        addEntity(new ItemEntity(stack, this, pixelX, pixelY));
    }

    private void spawnEnemyOnWalkableTile() {
        int x, y;
        do {
            x = random.nextInt(getYLen());
            y = random.nextInt(getXLen());
        } while (!getTiles()[y][x].isWalkable());

        int pixelX = x * getTileSize() + getTileSize() / 2;
        int pixelY = y * getTileSize() + getTileSize() / 2;
        Enemy enemy = new Enemy(this, pixelX, pixelY);
        if (getPlayer() != null) {
            enemy.setTarget(getPlayer());
        }
        addEntity(enemy);
    }
}
