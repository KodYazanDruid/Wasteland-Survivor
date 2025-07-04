package com.sonamorningstar.wastelandsurvivor.world.level;

import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.world.level.levels.DesertLevel;
import com.sonamorningstar.wastelandsurvivor.world.level.levels.ForestLevel;
import com.sonamorningstar.wastelandsurvivor.world.level.levels.HighwayLevel;

import java.util.HashMap;
import java.util.Map;

public class LevelManager {
    private final Map<String, Level> levelMap = new HashMap<>();
    private Level currentLevel;
    public LevelManager() {

    }

    public void setupLevels() {
        registerLevel("Desert", new DesertLevel("Desert", 32, 32, "154875"));
        registerLevel("Forest", new ForestLevel("Forest", 32, 32, "985154"));
        registerLevel("City", new Level("City", 32, 32,"356715"));
        registerLevel("Highway", new HighwayLevel("Highway", 5, 32, "123456"));
    }

    public void registerLevel(String name, Level level) {
        if (levelMap.containsKey(name)) {
            throw new IllegalArgumentException("Level with name " + name + " already exists.");
        }
        level.generateTiles();
        level.bootstrapEntities();
        levelMap.put(name, level);
    }
    public Level loadLevel(String name) {
        if (!levelMap.containsKey(name)) {
            throw new IllegalArgumentException("Level with name " + name + " does not exist.");
        }
        currentLevel = levelMap.get(name);
        return currentLevel;
    }

    public void update() {
        if (currentLevel != null) currentLevel.update();
    }

    public void draw(Canvas canvas) {
        if (currentLevel != null) currentLevel.draw(canvas);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

}
