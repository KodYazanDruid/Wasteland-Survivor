package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.manager.ImageManager;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;

public class ItemEntity extends Entity {
    private final ItemStack itemStack;

    public ItemEntity(ItemStack stack, Game game, Context context) {
        super(game, context, new BoundingBox(0, 0, 64, 64));
        this.itemStack = stack;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Bitmap image = ImageManager.getImage(getContext(), itemStack.getItem().getName().toLowerCase());
        if (image != null) {
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            paint.setAntiAlias(false);

            double x = getPosition().getX();
            double y = getPosition().getY();
            double endX = x + image.getWidth() * 4;
            double endY = y + image.getHeight() * 4;

            Rect destRect = new Rect((int) x, (int) y, (int) endX, (int) endY);
            canvas.drawBitmap(image, null, destRect, paint);
            Paint countStringPaint = new Paint();
            countStringPaint.setTextSize(50);
            countStringPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
            countStringPaint.setTypeface(Game.INSTANCE.edunReg);
            canvas.drawText(String.valueOf(itemStack.getCount()), (float) (endX), (float) (endY), countStringPaint);
        }
    }
}
