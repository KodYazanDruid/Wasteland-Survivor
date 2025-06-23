package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.GameLoop;
import com.sonamorningstar.wastelandsurvivor.world.Position;

public abstract class Entity {
    protected String name;
    protected Position position = Position.ORIGIN;
    protected Context context;
    // 0: left, 90: up, 180: right, 270: down
    private double rotation = 0; // Rotation in degrees (0-360), Counter-clockwise.

    protected double speed = 200; // Speed of the player in pixels per second
    protected double moveSpeed = speed / GameLoop.MAX_UPS; // Maximum speed of the player

    protected double velocityX = 0;
    protected double velocityY = 0;

    public Entity(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
    }

    public abstract void draw(Canvas canvas);

    public void update() {
        updateMovement();
    }

    public void addedToWorld() {

    }

    public void entitySpawned(Game game) {

    }

    protected void updateMovement() {
        double oldX = position.getX();
        double oldY = position.getY();
        setPosition(new Position(
                oldX + velocityX,
                oldY + velocityY
        ));
        double deltaX = oldX - position.getX();
        double deltaY = oldY - position.getY();
        if (deltaX != 0 || deltaY != 0) {
            setRotation(Math.toDegrees(Math.atan2(deltaY, deltaX)));
            if (rotation < 0) {
                setRotation(rotation + 360); // Normalize to 0-360 degrees
            }
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }
    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setDeltaMovement(double deltaX, double deltaY) {
        this.velocityX = deltaX;
        this.velocityY = deltaY;
    }

    public Position getPosition() {
        return position;
    }

    public double getDistance(Entity other) {
        return Math.sqrt(
                Math.pow(other.position.getX() - position.getX(), 2) +
                Math.pow(other.position.getY() - position.getY(), 2)
        );
    }
}
