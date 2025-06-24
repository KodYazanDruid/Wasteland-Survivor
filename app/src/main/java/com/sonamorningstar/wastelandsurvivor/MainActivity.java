package com.sonamorningstar.wastelandsurvivor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.androidgamesdk.GameActivity;

public class MainActivity extends GameActivity {
    static {
        System.loadLibrary("wastelandsurvivor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(new Game(this));

    }
}