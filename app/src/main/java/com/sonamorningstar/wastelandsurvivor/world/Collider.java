package com.sonamorningstar.wastelandsurvivor.world;

import android.graphics.Canvas;

public interface Collider {
     boolean intersects(Collider other);

    void drawDebug(Canvas canvas);
}
