package com.sonamorningstar.wastelandsurvivor.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.manager.ImageManager;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;

public class ItemDrawer {

    public static void drawItem(Canvas canvas, ItemStack stack, double x, double y) {
        Bitmap image = ImageManager.getImage(Game.INSTANCE.getContext(), stack.getItem().getName().toLowerCase());
        if (image != null) {
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            paint.setAntiAlias(false);

            double endX = x + image.getWidth() * 4;
            double endY = y + image.getHeight() * 4;

            Rect destRect = new Rect((int) x, (int) y, (int) endX, (int) endY);
            canvas.drawBitmap(image, null, destRect, paint);
            Paint countStringPaint = new Paint();
            countStringPaint.setTextSize(50);
            countStringPaint.setColor(ContextCompat.getColor(Game.INSTANCE.getContext(), R.color.white));
            countStringPaint.setTypeface(Game.INSTANCE.edunReg);
            canvas.drawText(String.valueOf(stack.getCount()), (float) (endX), (float) (endY), countStringPaint);
        }
    }
}
