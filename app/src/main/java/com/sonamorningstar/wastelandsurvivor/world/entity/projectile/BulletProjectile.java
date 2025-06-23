package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;

public class BulletProjectile extends Projectile {
    public BulletProjectile(Context context) {
        super(ProjectileType.BULLET, context);
    }

    @Override
    public void draw(Canvas canvas) {
        float x = (float) getPosition().getX();
        float y = (float) getPosition().getY();
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.purple_200));
        canvas.drawRect(x, y, x + 10, y + 10, paint);
    }

    @Override
    public void update() {
        super.update();
    }
}
