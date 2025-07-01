package com.sonamorningstar.wastelandsurvivor.world.level;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TickableTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level {
    private final String name;
    private final String seed;
    protected final SecureRandom random;

    // Dimensions of the level in tiles
    private final int width; // Columns (X-axis)
    private final int height; // Rows (Y-axis)

    private final List<Entity> entities = new CopyOnWriteArrayList<>();
    private Player player;

    private final Tile[][] tiles;
    private final List<TickableTile> tickableTiles = new CopyOnWriteArrayList<>();
    int tileSize = 128;

    public Level(String name, int width, int height, String seed) {
        this.name = name;
        this.seed = seed;
        this.width = width;
        this.height = height;
        // Standard: [rows][columns] -> [height][width]
        this.tiles = new Tile[this.height][this.width];
        random = new SecureRandom();
        random.setSeed(seed.getBytes());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<TickableTile> getTickableTiles() {
        return tickableTiles;
    }

    public void update() {
        for (Entity entity : entities) {
            if (!entity.markedForRemoval) entity.update(Game.INSTANCE);
            else entities.remove(entity);
        }
        for (TickableTile tickableTile : tickableTiles) {
            tickableTile.tick(this);
        }
    }

    public void generateTiles() {

    }

    public void bootstrapEntities() {

    }

    public void spawnItemOnWalkableTile(ItemStack stack) {

    }

    public void draw(Canvas canvas) {
        canvas.save();

        double cameraX = 0;
        double cameraY = 0;
        if (player != null) {
            cameraX = player.getPosition().getX() - canvas.getWidth() / 2.0;
            cameraY = player.getPosition().getY() - canvas.getHeight() / 2.0;
        }

        double levelWidth = this.width * this.tileSize;
        double levelHeight = this.height * this.tileSize;

        if (levelWidth < canvas.getWidth()) {
            cameraX = (levelWidth - canvas.getWidth()) / 2.0;
        } else {
            cameraX = Math.max(0.0, Math.min(cameraX, levelWidth - canvas.getWidth()));
        }

        if (levelHeight < canvas.getHeight()) {
            cameraY = (levelHeight - canvas.getHeight()) / 2.0;
        } else {
            cameraY = Math.max(0.0, Math.min(cameraY, levelHeight - canvas.getHeight()));
        }

        canvas.translate((float) -cameraX, (float) -cameraY);

        int startCol = (int) (cameraX / tileSize);
        int endCol = (int) ((cameraX + canvas.getWidth()) / tileSize) + 1;
        int startRow = (int) (cameraY / tileSize);
        int endRow = (int) ((cameraY + canvas.getHeight()) / tileSize) + 1;

        startRow = Math.max(0, startRow);
        startCol = Math.max(0, startCol);
        endRow = Math.min(height, endRow);
        endCol = Math.min(width, endCol);

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
        endX = Math.min(width - 1, endX);
        endY = Math.min(height - 1, endY);

        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                tilesInRegion.add(tiles[i][j]);
            }
        }
        return tilesInRegion;
    }

    public List<Entity> getEntitiesAboveTile(Tile tile) {
        List<Entity> entitiesAboveTile = new ArrayList<>();
        if (tile == null) return entitiesAboveTile;

        BoundingBox tileBox = tile.getCollision();
        for (Entity entity : getEntities()) {
            if (entity.getCollider().intersects(tileBox)) {
                entitiesAboveTile.add(entity);
            }
        }
        return entitiesAboveTile;
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
