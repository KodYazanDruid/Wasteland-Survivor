package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.graphics.Canvas;

import androidx.annotation.Nullable;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.engine.GameLoop;
import com.sonamorningstar.wastelandsurvivor.world.BoundingBox;
import com.sonamorningstar.wastelandsurvivor.world.BoundingCircle;
import com.sonamorningstar.wastelandsurvivor.world.Collider;
import com.sonamorningstar.wastelandsurvivor.world.Position;
import com.sonamorningstar.wastelandsurvivor.world.level.CollisionSide;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;
import com.sonamorningstar.wastelandsurvivor.world.level.tile.Tile;

public abstract class Entity {
    protected String name;
    protected Position position = Position.ORIGIN;
    protected Level level;
    protected final Collider collider;
    // 0: left, 90: up, 180: right, 270: down
    private double rotation = 0; // Rotation in degrees (0-360), Counter-clockwise.

    protected double speed = 200; // Speed of the player in pixels per second
    protected double moveSpeed = speed / GameLoop.MAX_UPS; // Maximum speed of the player

    protected double velocityX = 0;
    protected double velocityY = 0;


    public boolean markedForRemoval = false;

    public Entity(Level level, Collider collider) {
        this.level = level;
        this.collider = collider;
    }

    public Entity(Level level, Collider collider, double x, double y) {
        this(level, collider);
        this.position = new Position(x, y);
    }

    public double getRotation() {
        return rotation;
    }

    public Collider getCollider() {
        return collider;
    }

    public void changeLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() { return level; }

    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
    }

    public void draw(Canvas canvas) {
//        if (collider != null) {
//            collider.drawDebug(canvas);
//        }
    }

    public boolean isSolid() {
        return true;
    }

    public boolean canCollideWith(Entity other) {
        return this != other;
    }

    public void update(Game game) {
        double oldX = getPosition().getX();
        double oldY = getPosition().getY();

        updateMovement();

        double finalX = getPosition().getX();
        double finalY = getPosition().getY();
        double deltaX = oldX - finalX;
        double deltaY = oldY - finalY;

        if (deltaX != 0 || deltaY != 0) {
            setRotation(Math.toDegrees(Math.atan2(deltaY, deltaX)));
            if (getRotation() < 0) {
                setRotation(getRotation() + 360); // Normalize to 0-360 degrees
            }
        }
    }

    public void addedToWorld(Level level) {

    }
    public void aboutToBeRemoved(Level level) {

    }

    public void entitySpawned(Level level) {

    }

    public void onTileCollision(@Nullable Tile tile, CollisionSide side) {

    }
    public void onEntityCollision(Entity entity, CollisionSide side) {

    }

    protected void updateMovement() {
        if (level == null || (velocityX == 0 && velocityY == 0)) {
            return;
        }

        double originalX = getPosition().getX();
        if (velocityX != 0) {
            setPosition(new Position(originalX + velocityX, getPosition().getY()));
            updateColliderPosition();

            boolean collisionX = false;

            double levelWidth = level.getYLen() * level.getTileSize();
            BoundingBox box = getCollider().getBoundingBox();
            if (box.getX() < 0 || (box.getX() + box.getWidth()) > levelWidth) {
                onTileCollision(null, (velocityX > 0) ? CollisionSide.LEFT : CollisionSide.RIGHT);
                collisionX = true;
            }

            if (!collisionX){
                for (Tile tile : level.getTilesInRegion(getCollider().getBoundingBox())) {
                    if (!tile.isWalkable() && getCollider().intersects(tile.getCollision())) {
                        onTileCollision(tile, (velocityX > 0) ? CollisionSide.LEFT : CollisionSide.RIGHT);
                        collisionX = true;
                        break;
                    }
                }
            }

            if (!collisionX) {
                for (Entity other : level.getCollidingEntities(this)) {
                    if (!this.canCollideWith(other) || !other.canCollideWith(this)) continue;

                    CollisionSide side = (velocityX > 0) ? CollisionSide.LEFT : CollisionSide.RIGHT;
                    CollisionSide otherSide = (velocityX > 0) ? CollisionSide.RIGHT : CollisionSide.LEFT;

                    this.onEntityCollision(other, side);
                    other.onEntityCollision(this, otherSide);

                    if (this.isSolid() && other.isSolid()) {
                        collisionX = true;
                        break;
                    }
                }
            }

            if (collisionX) {
                setPosition(new Position(originalX, getPosition().getY()));
                updateColliderPosition();
            }
        }

        double originalY = getPosition().getY();
        if (velocityY != 0) {
            double currentX = getPosition().getX();
            setPosition(new Position(currentX, originalY + velocityY));
            updateColliderPosition();

            boolean collisionY = false;

            double levelHeight = level.getXLen() * level.getTileSize();
            BoundingBox box = getCollider().getBoundingBox();
            if (box.getY() < 0 || (box.getY() + box.getHeight()) > levelHeight) {
                onTileCollision(null, (velocityY > 0) ? CollisionSide.TOP : CollisionSide.BOTTOM);
                collisionY = true;
            }

            if (!collisionY) {
                for (Tile tile : level.getTilesInRegion(getCollider().getBoundingBox())) {
                    if (!tile.isWalkable() && getCollider().intersects(tile.getCollision())) {
                        onTileCollision(tile, (velocityY > 0) ? CollisionSide.TOP : CollisionSide.BOTTOM);
                        collisionY = true;
                        break;
                    }
                }
            }

            if (!collisionY) {
                for (Entity other : level.getCollidingEntities(this)) {
                    if (!this.canCollideWith(other) || !other.canCollideWith(this)) continue;

                    CollisionSide side = (velocityY > 0) ? CollisionSide.TOP : CollisionSide.BOTTOM;
                    CollisionSide otherSide = (velocityY > 0) ? CollisionSide.BOTTOM : CollisionSide.TOP;

                    this.onEntityCollision(other, side);
                    other.onEntityCollision(this, otherSide);

                    if (this.isSolid() && other.isSolid()) {
                        collisionY = true;
                        break;
                    }
                }
            }

            if (collisionY) {
                setPosition(new Position(currentX, originalY));
                updateColliderPosition();
            }
        }
    }

    private void updateColliderPosition() {
        if (collider instanceof BoundingBox) {
            BoundingBox box = (BoundingBox) collider;
            box.setBounds(position.getX() - box.getWidth() / 2, position.getY() - box.getHeight() / 2, box.getWidth(), box.getHeight());
        } else if (collider instanceof BoundingCircle) {
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
