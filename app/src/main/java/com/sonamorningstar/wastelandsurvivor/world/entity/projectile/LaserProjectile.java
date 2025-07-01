package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.LivingEntity;
import com.sonamorningstar.wastelandsurvivor.world.level.CollisionSide;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public class LaserProjectile extends Projectile {

    public LaserProjectile(Entity owner, Level level, double x, double y) {
        super(ProjectileType.LASER, owner, level, new BoundingBox(x, y, 100, 10), x, y);
        setRotation(owner.getRotation());
        setLifeTime(120);
    }

    @Override
    public void onTileCollision(Tile tile, CollisionSide side) {
        markedForRemoval = true;
    }

    @Override
    public void onEntityCollision(Entity entity, CollisionSide side) {
        if (!hit && entity instanceof LivingEntity) {
            hit = true;
            ((LivingEntity) entity).hurt(50);
            markedForRemoval = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        BoundingBox boundingBox = (BoundingBox) collider;
        float left = (float) boundingBox.getX();
        float top = (float) boundingBox.getY();
        float right = (float) (boundingBox.getX() + boundingBox.getWidth());
        float bottom = (float) (boundingBox.getY() + boundingBox.getHeight());
        canvas.save();
        canvas.rotate((float) getRotation(), (float) (left + (boundingBox.getWidth() / 2)), (float) (top + (boundingBox.getHeight() / 2)));
        Paint paint = new Paint();
        paint.setColor(0xFF0000FF);
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.restore();
    }

    @Override
    public void update(Game game) {
        if (velocityX == 0 && velocityY == 0) {
            double speed = 30.0; // Projectile speed
            double angleRadians = Math.toRadians(getRotation());
            double deltaX = -Math.cos(angleRadians) * speed;
            double deltaY = -Math.sin(angleRadians) * speed;
            setDeltaMovement(deltaX, deltaY);
        }

        super.update(game);
    }
}
