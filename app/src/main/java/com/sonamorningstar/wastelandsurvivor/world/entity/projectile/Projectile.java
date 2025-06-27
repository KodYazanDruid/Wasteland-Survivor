package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.Collider;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;
import com.sonamorningstar.wastelandsurvivor.world.level.Level;

public abstract class Projectile extends Entity {
    protected final Entity owner;
    protected final ProjectileType<? extends Projectile> type;
    protected boolean hit = false;
    protected int lifeTime = 600; // 10 seconds
    public Projectile(ProjectileType<? extends Projectile> type, Level level, Entity owner, Collider collider) {
        super(level, collider);
        this.type = type;
        this.owner = owner;
    }

    public Projectile(ProjectileType<? extends Projectile> type, Entity owner, Level level, Collider collider, double x, double y) {
        super(level, collider, x, y);
        this.type = type;
        this.owner = owner;
    }

    public ProjectileType<? extends Projectile> getType() {
        return type;
    }

    public int getLifeTime() {
        return lifeTime;
    }
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    public Entity getOwner() {
        return owner;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity other) {
        return super.canCollideWith(other) && other != getOwner();
    }

    @Override
    public void update(Game game) {
        super.update(game);
        if (getLifeTime() <= 0) markedForRemoval = true;
        else lifeTime--;
    }

}
