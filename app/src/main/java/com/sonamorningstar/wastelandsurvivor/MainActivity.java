package com.sonamorningstar.wastelandsurvivor;

import android.content.SharedPreferences;
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

        Game game = new Game(this);
        Game.INSTANCE = game;
        setContentView(game);

    }

    @Override
    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("WastelandSurvivor", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lastActivity", MainActivity.class.getCanonicalName());
        editor.commit();
        super.onPause();
    }
}