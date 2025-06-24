package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.GameLoop;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Collider;
import com.sonamorningstar.wastelandsurvivor.world.Position;

public abstract class Entity {
    protected String name;
    protected Position position = Position.ORIGIN;
    protected Context context;
    protected final Game game;
    protected final Collider collider;
    // 0: left, 90: up, 180: right, 270: down
    private double rotation = 0; // Rotation in degrees (0-360), Counter-clockwise.

    protected double speed = 200; // Speed of the player in pixels per second
    protected double moveSpeed = speed / GameLoop.MAX_UPS; // Maximum speed of the player

    protected double velocityX = 0;
    protected double velocityY = 0;

    public boolean markedForRemoval = false;

    public Entity(Game game, Context context, Collider collider) {
        this.game = game;
        this.context = context;
        this.collider = collider;
    }

    public Context getContext() {
        return context;
    }

    public double getRotation() {
        return rotation;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
    }

    public void draw(Canvas canvas) {
//        if (collider != null) {
//            collider.drawDebug(canvas);
//        }
    }

    public void update(Game game) {
        updateMovement();
    }

    public void addedToWorld(Game game) {

    }
    public void aboutToBeRemoved(Game game) {

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
        if (collider instanceof BoundingBox) {
            BoundingBox box = (BoundingBox) collider;
            box.setBounds(position.getX() - box.getWidth() / 2, position.getY() - box.getHeight() / 2, box.getWidth(), box.getHeight());
        } else if (collider instanceof BoundingCircle){
            BoundingCircle circle = (BoundingCircle) collider;
            circle.setBounds(position.getX(), position.getY(), circle.getRadius());
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
