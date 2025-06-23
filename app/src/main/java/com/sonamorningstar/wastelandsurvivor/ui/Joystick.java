package com.sonamorningstar.wastelandsurvivor.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private final int outerCircleCenterX;
    private final int outerCircleCenterY;
    private final int outerCircleRadius;

    private int innerCircleCenterX;
    private int innerCircleCenterY;
    private final int innerCircleRadius;

    private boolean isPressed = false;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int x, int y, int outerCircleRadius, int innerCircleRadius) {
        this.outerCircleCenterX = x;
        this.outerCircleCenterY = y;
        this.outerCircleRadius = outerCircleRadius;

        this.innerCircleCenterX = x;
        this.innerCircleCenterY = y;
        this.innerCircleRadius = innerCircleRadius;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(Color.GRAY);
        paint.setAlpha(96);
        canvas.drawCircle(outerCircleCenterX, outerCircleCenterY, outerCircleRadius, paint);

        paint.setColor(Color.DKGRAY);
        paint.setAlpha(96);
        canvas.drawCircle(innerCircleCenterX, innerCircleCenterY, innerCircleRadius, paint);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterX = (int) (outerCircleCenterX + actuatorX * outerCircleRadius);
        innerCircleCenterY = (int) (outerCircleCenterY + actuatorY * outerCircleRadius);
    }


    public boolean isPressed() {
        return isPressed;
    }

    public boolean checkPressed(float x, float y) {
        double joystickCenterToTouchDistance = Math.sqrt(Math.pow(x - outerCircleCenterX, 2) + Math.pow(y - outerCircleCenterY, 2));
        return joystickCenterToTouchDistance <=  outerCircleRadius;
    }

    public void setPressed(boolean b) {
        this.isPressed = b;
    }

    public void setActuator(float x, float y) {
        double deltaX = x - outerCircleCenterX;
        double deltaY = y - outerCircleCenterY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (distance <= outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        } else {
            actuatorX = deltaX / distance;
            actuatorY = deltaY / distance;
        }
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
