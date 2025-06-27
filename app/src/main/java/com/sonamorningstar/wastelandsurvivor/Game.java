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

import com.sonamorningstar.wastelandsurvivor.engine.GameLoop;
import com.sonamorningstar.wastelandsurvivor.ui.Joystick;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.entity.Player;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.LevelManager;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameLoop gameLoop;
    public long ticks;

    private final LevelManager levelManager;
    public final Player player;
    private final Joystick joystick;

    public final Typeface edunReg;

    public static Game INSTANCE;

    public Game(Context context) {
        super(context);
        edunReg = getResources().getFont(R.font.edunreg);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.gameLoop = new GameLoop(this, holder);
        this.levelManager = new LevelManager();
        this.joystick = new Joystick(250, 750, 250, 125);
        this.player = new Player(levelManager.getCurrentLevel());

        setFocusable(true);
    }

    public double getDeltaTime() {
        return gameLoop.getDeltaTime();
    }

    public void update() {
        ticks++;
        joystick.update();
        player.handleJoystick(joystick);
        if (levelManager.getCurrentLevel() != null) levelManager.getCurrentLevel().update();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        levelManager.setupLevels();
        Level currentLevel = levelManager.loadLevel("Desert"); // Load the initial level
        player.changeLevel(currentLevel);
        player.setPosition(new Position(128, 128));
        currentLevel.addEntity(player);
        currentLevel.generateTiles();
        currentLevel.bootstrapEntities();

        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed()) {
                    player.fireProjectile(event.getX(), event.getY());
                } else if (joystick.checkPressed(event.getX(), event.getY())) {
                    joystick.setPressed(true);
                } else player.fireProjectile(event.getX(), event.getY());
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

        if (levelManager.getCurrentLevel() != null) levelManager.getCurrentLevel().draw(canvas);

        joystick.draw(canvas);

        drawUPS(canvas);
        drawFPS(canvas);
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
