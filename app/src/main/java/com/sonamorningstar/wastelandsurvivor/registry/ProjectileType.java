package com.sonamorningstar.wastelandsurvivor.registry;

import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.BulletProjectile;
import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.LaserProjectile;
import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.Projectile;
import com.sonamorningstar.wastelandsurvivor.world.entity.projectile.RocketProjectile;

public class ProjectileType<Projectile> {

    public static final ProjectileType<BulletProjectile> BULLET = new ProjectileType<>("bullet");
    public static final ProjectileType<RocketProjectile> ROCKET = new ProjectileType<>("rocket");
    public static final ProjectileType<LaserProjectile> LASER = new ProjectileType<>("laser");

    private final String name;

    private ProjectileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ProjectileType{" +
                "name='" + name + '\'' +
                '}';
    }
}
