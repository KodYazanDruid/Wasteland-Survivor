package com.sonamorningstar.wastelandsurvivor.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private static final Map<String, Bitmap> imageCache = new HashMap<>();

    public static Bitmap getImage(Context context, String imageName) {
        if (!imageCache.containsKey(imageName)) {
            int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resourceId != 0) {
                imageCache.put(imageName, BitmapFactory.decodeResource(
                        context.getResources(), resourceId));
            }
        }
        return imageCache.get(imageName);
    }
}
