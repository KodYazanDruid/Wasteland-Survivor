package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.GameLoop;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.LivingEntity;

public class LaserProjectile extends Projectile {
    public LaserProjectile(Entity owner, Game game, Context context) {
        super(ProjectileType.LASER, game, owner, context, new BoundingBox(0, 0, 100, 10));
        setPosition(owner.getPosition().copy());
        setRotation(owner.getRotation());
        setLifeTime(120);
    }

    @Override
    public void draw(Canvas canvas) {
        BoundingBox boundingBox = (BoundingBox) collider;
        float left = (float) boundingBox.getX();
        float top = (float) boundingBox.getY();
        float right = (float) (boundingBox.getX() + boundingBox.getWidth());
        float bottom = (float) (boundingBox.getY() + boundingBox.getHeight());
        canvas.save();
        canvas.rotate((float) getRotation(), (left + right) / 2, (top + bottom) / 2);
        Paint paint = new Paint();
        paint.setColor(0xFF0000FF);
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.restore();
    }

    @Override
    protected void onHitEntity(Entity target) {
        if (target instanceof LivingEntity) {
            hit = true;
            ((LivingEntity) target).hurt(50);
            markedForRemoval = true;
        }
    }

    @Override
    public void update(Game game) {
        if (velocityX == 0 && velocityY == 0) {
            double speed = 10.0; // Projectile speed
            double angleRadians = Math.toRadians(getRotation());
            double deltaX = -Math.cos(angleRadians) * speed;
            double deltaY = -Math.sin(angleRadians) * speed;
            setDeltaMovement(deltaX, deltaY);
        }

        if (!hit) {
            for (Entity entity : game.getEntities()) {
                if (entity != null && ! entity.equals(this) && !entity.equals(owner) &&
                        collider.intersects(entity.getCollider())) {
                    onHitEntity(entity);
                }
            }
        }

        super.update(game);
    }
}
