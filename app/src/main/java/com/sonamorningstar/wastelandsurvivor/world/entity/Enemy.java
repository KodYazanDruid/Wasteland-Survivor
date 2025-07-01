package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class Enemy extends LivingEntity {
    private Entity target;

    public Enemy(Level level, double x, double y) {
        super(level, new BoundingCircle(x, y, 30), x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(Game.INSTANCE.getContext(), R.color.teal_700));
        BoundingCircle bounds = (BoundingCircle) collider;
        canvas.drawCircle((float)getPosition().getX(), (float)getPosition().getY(), (float)bounds.getRadius(), paint);
        super.draw(canvas);
    }

    @Override
    public void update(Game game) {
        super.update(game);
        if (target instanceof Player) {
            double dist = getDistance(target);
            setDeltaMovement((target.getPosition().getX() - position.getX()) / dist * speed * 0.6,
                             (target.getPosition().getY() - position.getY()) / dist * speed * 0.6);
        }
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
