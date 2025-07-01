package com.sonamorningstar.wastelandsurvivor.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.sonamorningstar.wastelandsurvivor.Game;

public class GameLoop extends Thread {
    public static final int MAX_UPS = 60;
    public static final int MAX_FPS = 60;
    private static final double UPS_PERIOD = 1_000_000_000.0 / MAX_UPS;
    private static final double FPS_PERIOD = 1_000_000_000.0 / MAX_FPS;
    private boolean isRunning;
    private final Game game;
    private final SurfaceHolder surfaceHolder;

    private double averageUPS;
    private double averageFPS;

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

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (isRunning) {
            long now = System.nanoTime();
            deltaU += (now - previousTime) / UPS_PERIOD;
            deltaF += (now - previousTime) / FPS_PERIOD;
            previousTime = now;

            if (deltaU >= 1.0) {
                game.update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1.0) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        game.draw(canvas);
                        frames++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                averageFPS = frames;
                averageUPS = updates;
                frames = 0;
                updates = 0;
            }
        }
    }
}
