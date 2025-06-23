package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.R;

public class Enemy extends LivingEntity {
    private Entity target;

    public Enemy(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.teal_700));
        canvas.drawCircle((float)getPosition().getX(), (float)getPosition().getY(), 30, paint);
    }

    @Override
    public void update() {
        super.update();
        if (target instanceof Player) {
            double dist = getDistance(target);
            setDeltaMovement((target.getPosition().getX() - position.getX()) / dist * moveSpeed * 0.6,
                             (target.getPosition().getY() - position.getY()) / dist * moveSpeed * 0.6);
        }
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
