package com.sonamorningstar.wastelandsurvivor.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.sonamorningstar.wastelandsurvivor.Game;

public class GameLoop extends Thread {
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3 / MAX_UPS;
    private boolean isRunning;
    private final Game game;
    private final SurfaceHolder surfaceHolder;

    private double averageUPS;
    private double averageFPS;
    private double deltaTime;

    public GameLoop(Game game, SurfaceHolder holder) {
        this.game = game;
        this.surfaceHolder = holder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game loop logic.
        Canvas canvas = null;

        startTime = System.currentTimeMillis();
        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        game.draw(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }


            elapsedTime = System.currentTimeMillis() - startTime;
            deltaTime = elapsedTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (elapsedTime * 1E-3);
                averageFPS = frameCount / (elapsedTime * 1E-3);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}
