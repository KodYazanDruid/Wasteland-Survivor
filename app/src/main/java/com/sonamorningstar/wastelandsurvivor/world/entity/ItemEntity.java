package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.manager.ImageManager;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;

public class ItemEntity extends Entity {
    private final ItemStack itemStack;
    public ItemEntity(ItemStack stack, Game game, Context context) {
        super(game, context, new BoundingBox(0, 0, 50, 50));
        this.itemStack = stack;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Bitmap image = ImageManager.getImage(getContext(), itemStack.getItem().getName().toLowerCase());
        if (image != null) {
            canvas.save();
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            paint.setAntiAlias(false);

            float x = (float) getPosition().getX();
            float y = (float) getPosition().getY();

            Rect destRect = new Rect((int)x, (int)y, (int)(x + image.getWidth() * 2), (int)(y + image.getHeight() * 2));
            canvas.drawBitmap(image, null, destRect, paint);

            canvas.restore();
        }
    }
}
