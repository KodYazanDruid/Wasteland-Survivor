package com.sonamorningstar.wastelandsurvivor.world.level.placer;

import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class PatternPlacer {

    /**
     * Places a TilePattern onto the level's tile grid.
     *
     * @param level      The level to place the pattern in.
     * @param pattern    The pattern to place.
     * @param startTileX The starting tile column (X) for the pattern's top-left corner.
     * @param startTileY The starting tile row (Y) for the pattern's top-left corner.
     * @param overwrite  If true, will overwrite existing tiles. If false, will only place on null tiles.
     */
    public static void place(Level level, TilePattern pattern, int startTileX, int startTileY, boolean overwrite) {
        Tile[][] levelTiles = level.getTiles();
        Tile[][] patternTiles = pattern.getTiles();

        for (int y = 0; y < pattern.getHeight(); y++) {
            for (int x = 0; x < pattern.getWidth(); x++) {
                int targetX = startTileX + x;
                int targetY = startTileY + y;

                // Boundary check to ensure we don't write outside the level's grid
                if (targetY >= 0 && targetY < level.getWidth() && targetX >= 0 && targetX < level.getHeight()) {
                    Tile patternTile = patternTiles[y][x];
                    if (patternTile != null && (overwrite || levelTiles[targetY][targetX] == null)) {
                        // Create a new instance of the tile to avoid sharing objects
                        levelTiles[targetY][targetX] = new Tile(patternTile.getName(), patternTile.getBackgroundTile(), patternTile.isWalkable());
                    }
                }
            }
        }
    }
}