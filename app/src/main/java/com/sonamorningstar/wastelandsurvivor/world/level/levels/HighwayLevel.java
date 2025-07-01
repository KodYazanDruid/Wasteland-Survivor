package com.sonamorningstar.wastelandsurvivor.world.level.levels;

import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TeleporterTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class HighwayLevel extends Level {
    public HighwayLevel(String name, int width, int height, String seed) {
        super(name, width, height, seed);
    }

    @Override
    public void generateTiles() {
        Tile road = new Tile("dirt", true);
        Tile grass = new Tile("grass", true);

        for (int y = 0; y < getHeight(); y++) { // y is row
            for (int x = 0; x < getWidth(); x++) { // x is column
                if (x >= getWidth() / 2 - 1 && x <= getWidth() / 2 + 1) {
                    getTiles()[y][x] = new Tile(road.getName(), road.getBackgroundTile(), road.isWalkable());
                } else {
                    getTiles()[y][x] = new Tile(grass.getName(), grass.getBackgroundTile(), grass.isWalkable());
                }
            }
        }

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (getTiles()[y][x] != null) {
                    getTiles()[y][x].setPosition(x * getTileSize(), y * getTileSize());
                }
            }
        }
        var tele = new TeleporterTile("yellow_tree", "dirt", true);
        tele.setDestinationLevel("Forest");
        tele.setPosition(256, 1280);
        getTiles()[10][2] = tele;
        getTickableTiles().add(tele);
    }
}