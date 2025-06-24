package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.R;
import com.sonamorningstar.wastelandsurvivor.ui.Joystick;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Position;

public class Player extends LivingEntity {

    public Player(Game game, Context context) {
        super(game, context, new BoundingCircle(0, 0, 40));
    }

    @Override
    public void draw(Canvas canvas) {
        Position position = getPosition();
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.teal_200));
        canvas.drawCircle((float) position.getX(), (float) position.getY(), 40, paint);
        super.draw(canvas);
    }

    @Override
    public void update(Game game) {
        super.update(game);
    }

    public void handleJoystick(Joystick joystick) {
        setDeltaMovement(joystick.getActuatorX() * moveSpeed, joystick.getActuatorY() * moveSpeed);
    }
}
