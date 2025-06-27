package com.sonamorningstar.wastelandsurvivor.world.level;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.registry.AllItems;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Enemy;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.ItemEntity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level {
    private final String name;
    private final String seed;
    private final SecureRandom random;

    // Dimensions of the level in tiles
    private final int xLen;
    private final int yLen;

    private final List<Entity> entities = new CopyOnWriteArrayList<>();
    private Player player;

    private final Tile[][] tiles;
    int tileSize = 128;

    public Level(String name, int xLen, int yLen, String seed) {
        this.name = name;
        this.seed = seed;
        this.xLen = xLen;
        this.yLen = yLen;
        this.tiles = new Tile[xLen][yLen];
        random = new SecureRandom(seed.getBytes());
    }

    public int getXLen() {
        return xLen;
    }

    public int getYLen() {
        return yLen;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void update() {
        for (Entity entity : entities) {
            if (!entity.markedForRemoval) entity.update(Game.INSTANCE);
            else entities.remove(entity);
        }
    }

    public void generateTiles() {
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                /*Tile tile;
                if (i == 0 && j == 0) {
                    tile = new Tile("sand", true);
                } else {
                    tile = random.nextBoolean() ? new Tile("sand", true) : new Tile("yellow_tree", "sand", false);
                }*/
                Tile tile = new Tile("grassy", true);
                tile.setPosition(j * tileSize, i * tileSize);
                tile.loadBitmaps();
                tiles[i][j] = tile;
            }
        }
    }

    public void bootstrapEntities() {
        ItemEntity apple = new ItemEntity(AllItems.APPLE.createStack(1), this, 500, 500);
        addEntity(apple);
        Enemy enemy = new Enemy(this, 600, 600);
        enemy.setTarget(player);
        addEntity(enemy);
    }

    public void draw(Canvas canvas) {
        canvas.save();

        double cameraX = 0;
        double cameraY = 0;
        if (player != null) {
            cameraX = player.getPosition().getX() - canvas.getWidth() / 2.0;
            cameraY = player.getPosition().getY() - canvas.getHeight() / 2.0;
        }

        double levelWidth = this.xLen * this.tileSize;
        double levelHeight = this.yLen * this.tileSize;

        cameraX = Math.max(0.0, Math.min(cameraX, levelWidth - canvas.getWidth()));
        cameraY = Math.max(0.0, Math.min(cameraY, levelHeight - canvas.getHeight()));

        canvas.translate((float) -cameraX, (float) -cameraY);

        int startCol = (int) (cameraX / tileSize);
        int endCol = (int) ((cameraX + canvas.getWidth()) / tileSize) + 1;
        int startRow = (int) (cameraY / tileSize);
        int endRow = (int) ((cameraY + canvas.getHeight()) / tileSize) + 1;

        startCol = Math.max(0, startCol);
        startRow = Math.max(0, startRow);
        endCol = Math.min(tiles[0].length, endCol);
        endRow = Math.min(tiles.length, endRow);

        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].draw(canvas);
                }
            }
        }

        for (Entity entity : entities) {
            if (!entity.markedForRemoval) entity.draw(canvas);
        }

        canvas.restore();
    }

    public void addEntity(Entity entity) {
        entity.addedToWorld(this);
        entities.add(entity);
        if (entity instanceof Player) player = (Player) entity;
        entity.entitySpawned(this);
    }
    public void removeEntity(Entity entity) {
        entity.aboutToBeRemoved(this);
        entities.remove(entity);
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public List<Tile> getTilesInRegion(BoundingBox box) {
        List<Tile> tilesInRegion = new ArrayList<>();
        if (box == null) return tilesInRegion;

        // Calculate tile indices that the bounding box overlaps with
        int startX = (int) Math.floor(box.getX() / tileSize);
        int startY = (int) Math.floor(box.getY() / tileSize);
        int endX = (int) Math.floor((box.getX() + box.getWidth()) / tileSize);
        int endY = (int) Math.floor((box.getY() + box.getHeight()) / tileSize);

        // Clamp values to be within the grid bounds
        startX = Math.max(0, startX);
        startY = Math.max(0, startY);
        endX = Math.min(tiles[0].length - 1, endX);
        endY = Math.min(tiles.length - 1, endY);

        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                tilesInRegion.add(tiles[i][j]);
            }
        }
        return tilesInRegion;
    }

    public List<Entity> getCollidingEntities(Entity entity) {
        List<Entity> collidingEntities = new ArrayList<>();
        for (Entity inLevel : getEntities()) {
            if (!Objects.equals(entity, inLevel) && inLevel.getCollider().intersects(entity.getCollider()))
                collidingEntities.add(inLevel);
        }
        return collidingEntities;
    }
}
