package com.sonamorningstar.wastelandsurvivor.world;

public class Position {
    private final double x;
    private final double y;

    public static final Position ORIGIN = new Position(0, 0);

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Position copy() {
        return new Position(x, y);
    }
}
