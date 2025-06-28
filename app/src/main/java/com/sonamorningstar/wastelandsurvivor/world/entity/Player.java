package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.drawer.ItemDrawer;
import com.sonamorningstar.wastelandsurvivor.manager.ImageManager;
import com.sonamorningstar.wastelandsurvivor.object.ItemStack;
import com.sonamorningstar.wastelandsurvivor.ui.Joystick;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.LaserProjectile;
import com.sonamorningstar.wastelandsurvivor.world.level.CollisionSide;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public class Player extends LivingEntity {

    public Player(Level level) {
        super(level, new BoundingCircle(0, 0, 40));
    }

    @Override
    public void draw(Canvas canvas) {
        Position position = getPosition();
        Paint paint = new Paint();
        Context context = Game.INSTANCE.getContext();
        paint.setColor(ContextCompat.getColor(context, R.color.teal_200));
        canvas.drawCircle((float) position.getX(), (float) position.getY(), 40, paint);
        super.draw(canvas);
    }

    @Override
    public void onEntityCollision(Entity entity, CollisionSide side) {
        if (entity instanceof ItemEntity) {
            ItemEntity itemEntity = (ItemEntity) entity;
            itemEntity.markedForRemoval = true;
            ItemStack itemStack = itemEntity.getItemStack();
            itemInventory.addItem(itemStack);
        }
    }

    public void handleJoystick(Joystick joystick) {
        setDeltaMovement(joystick.getActuatorX() * moveSpeed, joystick.getActuatorY() * moveSpeed);
    }

    public void fireProjectile(float x, float y) {
        LaserProjectile laser = new LaserProjectile(this, level, getPosition().getX() - ((BoundingCircle) collider).getRadius(), getPosition().getY());
        level.addEntity(laser);
    }
}
