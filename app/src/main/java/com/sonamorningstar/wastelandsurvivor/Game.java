package com.sonamorningstar.wastelandsurvivor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.ui.Joystick;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.Enemy;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Player player;
    private final Joystick joystick;

    private final List<Entity> entities = new ArrayList<>();
    public Game(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.gameLoop = new GameLoop(this, holder);
        this.player = new Player(context);
        player.setPosition(new Position(150, 150));
        addEntity(player);
        Enemy enemy = new Enemy(context);
        enemy.setTarget(player);
        enemy.setPosition(new Position(1000, 500));
        addEntity(enemy);
        this.joystick = new Joystick(275, 700, 175, 85);
        setFocusable(true);
    }

    public void update() {
        joystick.update();
        for (Entity entity : entities) {
            entity.update();
        }
        player.handleJoystick(joystick);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public void addEntity(Entity entity) {
        entity.addedToWorld();
        entities.add(entity);
        entity.entitySpawned(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.checkPressed(event.getX(), event.getY())) {
                    joystick.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.isPressed()) {
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        for (Entity entity : entities) {
            double x = entity.getPosition().getX();
            double y = entity.getPosition().getY();
            double rotation = entity.getRotation();
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
            canvas.drawText("X: " + x, (float) x, (float) y - 140, paint);
            canvas.drawText("Y: " + y, (float) x, (float) y - 100, paint);
            canvas.drawText("Rotation: " + rotation, (float) x, (float) y - 60, paint);
            entity.draw(canvas);
        }
        joystick.draw(canvas);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.purple_500);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 10, 40, paint);
    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.purple_700);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 10, 100, paint);
    }
}
