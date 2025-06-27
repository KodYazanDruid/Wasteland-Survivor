package com.sonamorningstar.wastelandsurvivor.world.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.manager.ImageManager;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;

public class Tile {
    private final String name;
    private final String backgroundTile;
    private final boolean walkable;
    private BoundingBox bBox;
    private boolean dirty = false;

    private int x,y;

    private Bitmap image;
    private Bitmap backgroundImage;
    private final Paint paint = new Paint();
    private Rect destRect;

    public Tile(String name, boolean walkable) {
        this.name = name;
        this.backgroundTile = "";
        this.walkable = walkable;
        initialize();
    }

    public Tile(String name, String backgroundTile, boolean walkable) {
        this.name = name;
        this.backgroundTile = backgroundTile;
        this.walkable = walkable;
        initialize();
    }

    private void initialize() {
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }

    public void loadBitmaps() {
        if (backgroundImage == null && !backgroundTile.isBlank()) {
            backgroundImage = ImageManager.getImage(Game.INSTANCE.getContext(), backgroundTile.toLowerCase() + "_tile");
        }
        if (image == null && !name.isBlank()) {
            image = ImageManager.getImage(Game.INSTANCE.getContext(), getName().toLowerCase() + "_tile");
        }
    }

    public String getName() {
        return name;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public BoundingBox getCollision() {
        if (bBox == null || dirty) {
            bBox = createCollision();
            dirty = false;
        }
        return bBox;
    }

    protected BoundingBox createCollision() {
        return new BoundingBox(x, y, 128, 128);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.destRect = new Rect(x, y, x + 128, y + 128);
        this.dirty = true;
    }

    public void draw(Canvas canvas) {
        if (backgroundImage != null) {
            canvas.drawBitmap(backgroundImage, null, destRect, paint);
        }
        if (image != null) {
            canvas.drawBitmap(image, null, destRect, paint);
        }
    }
}
