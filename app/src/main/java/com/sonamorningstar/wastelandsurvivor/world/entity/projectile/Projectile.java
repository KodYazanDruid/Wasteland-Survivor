package com.sonamorningstar.wastelandsurvivor.world.entity.projectile;

import android.content.Context;

import com.sonamorningstar.wastelandsurvivor.Game;
import com.sonamorningstar.wastelandsurvivor.registry.ProjectileType;
import com.sonamorningstar.wastelandsurvivor.world.Collider;
import com.sonamorningstar.wastelandsurvivor.world.entity.Entity;

public abstract class Projectile extends Entity {
    protected final Entity owner;
    protected final ProjectileType<? extends Projectile> type;
    protected boolean hit = false;
    protected int lifeTime = 600; // 10 seconds
    public Projectile(ProjectileType<? extends Projectile> type, Game game, Entity owner, Context context, Collider collider) {
        super(game, context, collider);
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
    public void update(Game game) {
        super.update(game);
        if (getLifeTime() <= 0) markedForRemoval = true;
        else lifeTime--;
    }

    protected void onHitEntity(Entity target) {

    }

}
