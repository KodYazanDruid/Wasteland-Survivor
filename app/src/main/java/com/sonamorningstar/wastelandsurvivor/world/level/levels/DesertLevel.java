package com.sonamorningstar.wastelandsurvivor.world.level.levels;

import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.registry.AllItems;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.ItemEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.TeleporterTile;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class DesertLevel extends Level {
    public DesertLevel(String name, int xLen, int yLen, String seed) {
        super(name, xLen, yLen, seed);
    }

    @Override
    public void generateTiles() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Tile tile = i == 0 || j == 0 || i == getHeight() - 1 || j == getWidth() - 1 ?
                        new Tile("yellow_tree", "sand", false) : new Tile("sand", true);
                tile.setPosition(j * getTileSize(), i * getTileSize());
                getTiles()[i][j] = tile;
            }
        }

        var teleporter = new TeleporterTile("green_tree", "sand", true, "Highway");
        teleporter.setPosition(3 * 128, 3 * 128);
        getTiles()[3][3] = teleporter;
        getTickableTiles().add(teleporter);
    }

    @Override
    public void bootstrapEntities() {
        for (int i = 0; i < 5; i++) {
            int x = 1 + random.nextInt(getWidth() - 2);
            int y = 1 + random.nextInt(getHeight() - 2);
            ItemEntity itemEntity = new ItemEntity(AllItems.SAND_PILE.createStack(1), this, x * 128, y * 128);
            itemEntity.setPosition(new Position(x * getTileSize(), y * getTileSize()));
            addEntity(itemEntity);
        }
    }
}
