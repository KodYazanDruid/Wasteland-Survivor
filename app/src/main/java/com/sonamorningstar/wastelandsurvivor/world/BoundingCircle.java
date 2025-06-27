package com.sonamorningstar.wastelandsurvivor.world;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BoundingCircle implements Collider {

    private double centerX, centerY, radius;

    public BoundingCircle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public void setBounds(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public double getCenterX() { return centerX; }
    public double getCenterY() { return centerY; }
    public double getRadius() { return radius; }

    @Override
    public boolean intersects(Collider other) {
        if (other instanceof BoundingCircle) {
            // Circle-Circle collision
            BoundingCircle otherCircle = (BoundingCircle) other;
            double dx = this.centerX - otherCircle.centerX;
            double dy = this.centerY - otherCircle.centerY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            return distance <= this.radius + otherCircle.radius;
        } else if (other instanceof BoundingBox) {
            // Circle-Box collision (delegate to box's implementation)
            return other.intersects(this);
        }
        return false;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    @Override
    public void drawDebug(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle((float) centerX, (float) centerY, (float) radius, paint);
    }
}
