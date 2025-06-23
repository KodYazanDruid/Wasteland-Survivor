package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.world.Position;

public class Player extends LivingEntity {

    public Player(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        Position position = getPosition();
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.teal_200));
        canvas.drawCircle((float) position.getX(), (float) position.getY(), 40, paint);
    }

    @Override
    public void update() {

    }
}
