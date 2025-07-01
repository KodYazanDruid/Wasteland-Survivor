package com.sonamorningstar.wastelandsurvivor.world.level.placer;

import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class TilePattern {
    private final Tile[][] pattern;
    private final int width;
    private final int height;

    public TilePattern(int width, int height) {
        this.width = width;
        this.height = height;
        this.pattern = new Tile[height][width];
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            this.pattern[y][x] = tile;
        }
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return pattern[y][x];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return pattern;
    }
}