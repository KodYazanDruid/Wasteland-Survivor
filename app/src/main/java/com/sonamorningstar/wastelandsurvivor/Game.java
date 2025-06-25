package com.sonamorningstar.wastelandsurvivor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.sonamorningstar.wastelandsurvivor.registry.AllItems;
import com.sonamorningstar.wastelandsurvivor.ui.Joystick;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.Enemy;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.entity.ItemEntity;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;
import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.LaserProjectile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    private final Player player;
    private final Joystick joystick;
    public long ticks;

    private final List<Entity> entities = new CopyOnWriteArrayList<>();

    public final Typeface edunReg;

    public static Game INSTANCE;

    public Game(Context context) {
        super(context);
        edunReg = getResources().getFont(R.font.edunreg);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.gameLoop = new GameLoop(this, holder);
        this.player = new Player(this, context);
        player.setPosition(new Position(150, 150));
        addEntity(player);
        Enemy enemy = new Enemy(this, context);
        enemy.setTarget(player);
        enemy.setPosition(new Position(1000, 500));
        addEntity(enemy);
        ItemEntity appleEntity = new ItemEntity(AllItems.APPLE.createStack(1), this, context);
        appleEntity.setPosition(new Position(500, 500));
        addEntity(appleEntity);
        this.joystick = new Joystick(275, 700, 175, 85);
        setFocusable(true);
    }

    public double getDeltaTime() {
        return gameLoop.getDeltaTime();
    }

    public void update() {
        ticks++;
        joystick.update();
        player.handleJoystick(joystick);
        for (Entity entity : entities) {
            if (!entity.markedForRemoval) entity.update(this);
            else removeEntity(entity);
        }
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
        entity.addedToWorld(this);
        entities.add(entity);
        entity.entitySpawned(this);
    }
    public void removeEntity(Entity entity) {
        entity.aboutToBeRemoved(this);
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed()) {
                    addEntity(new LaserProjectile(player, this, getContext()));
                } else if (joystick.checkPressed(event.getX(), event.getY())) {
                    joystick.setPressed(true);
                } else addEntity(new LaserProjectile(player, this, getContext()));
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
//            double x = entity.getPosition().getX();
//            double y = entity.getPosition().getY();
//            double rotation = entity.getRotation();
//            Paint paint = new Paint();
//            paint.setTextSize(50);
//            paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
//            canvas.drawText("X: " + x, (float) x, (float) y - 140, paint);
//            canvas.drawText("Y: " + y, (float) x, (float) y - 100, paint);
//            canvas.drawText("Rotation: " + rotation, (float) x, (float) y - 60, paint);
            if (!entity.markedForRemoval) entity.draw(canvas);
        }
        joystick.draw(canvas);
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.purple_500);
        paint.setColor(color);
        paint.setTextSize(50);
        paint.setTypeface(Game.INSTANCE.edunReg);
        canvas.drawText("UPS: " + averageUPS, 10, 40, paint);
    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.purple_700);
        paint.setColor(color);
        paint.setTextSize(50);
        paint.setTypeface(Game.INSTANCE.edunReg);
        canvas.drawText("FPS: " + averageFPS, 10, 100, paint);
    }
}
