package com.sonamorningstar.wastelandsurvivor.world;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BoundingBox implements Collider {
    private double x, y, width, height;

    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    @Override
    public boolean intersects(Collider other) {
        if (other instanceof BoundingBox) {
            // Box-Box collision
            BoundingBox otherBox = (BoundingBox) other;
            return this.x < otherBox.x + otherBox.width &&
                    this.x + this.width > otherBox.x &&
                    this.y < otherBox.y + otherBox.height &&
                    this.y + this.height > otherBox.y;
        } else if (other instanceof BoundingCircle) {
            // Box-Circle collision
            BoundingCircle circle = (BoundingCircle) other;
            // Find closest point on rectangle to circle center
            double closestX = Math.max(this.x, Math.min(circle.getCenterX(), this.x + this.width));
            double closestY = Math.max(this.y, Math.min(circle.getCenterY(), this.y + this.height));

            // Calculate distance between closest point and circle center
            double distanceX = circle.getCenterX() - closestX;
            double distanceY = circle.getCenterY() - closestY;
            double distanceSquared = distanceX * distanceX + distanceY * distanceY;

            return distanceSquared <= circle.getRadius() * circle.getRadius();
        }
        return false;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this;
    }

    @Override
    public void drawDebug(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        canvas.drawRect((float)x, (float)y, (float)(x + width), (float)(y + height), paint);
    }
}
