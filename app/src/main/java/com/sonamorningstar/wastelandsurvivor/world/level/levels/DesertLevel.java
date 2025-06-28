package com.sonamorningstar.wastelandsurvivor.world.level.levels;

import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class DesertLevel extends Level {
    public DesertLevel(String name, int xLen, int yLen, String seed) {
        super(name, xLen, yLen, seed);
    }

    @Override
    public void generateTiles() {
        for (int i = 0; i < getXLen(); i++) {
            for (int j = 0; j < getYLen(); j++) {
                Tile tile = new Tile("sand", true);
                tile.setPosition(j * getTileSize(), i * getTileSize());
                getTiles()[i][j] = tile;
            }
        }
    }
}
