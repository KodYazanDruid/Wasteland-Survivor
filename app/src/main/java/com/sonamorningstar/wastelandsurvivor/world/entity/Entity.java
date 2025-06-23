package com.sonamorningstar.wastelandsurvivor.world.entity;

import android.content.Context;
import android.graphics.Canvas;

import com.sonamorningstar.wastelandsurvivor.world.Position;

public abstract class Entity {
    protected String name;
    protected Position position = Position.ORIGIN;
    protected Context context;

    public Entity(Context context) {
        this.context = context;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
