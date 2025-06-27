package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class BulletProjectile extends Projectile {
    public BulletProjectile(Entity owner, Level level) {
        super(ProjectileType.BULLET, level, owner, new BoundingCircle(0, 0, 5));
    }

    @Override
    public void draw(Canvas canvas) {
        float x = (float) getPosition().getX();
        float y = (float) getPosition().getY();
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(Game.INSTANCE.getContext(), R.color.purple_200));
        canvas.drawRect(x, y, x + 10, y + 10, paint);
    }

    @Override
    public void update(Game game) {
        super.update(game);
    }
}
