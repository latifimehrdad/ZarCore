package com.zar.core.view.picker.date.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.collection.SimpleArrayMap;

public class FontUtils {
    private static String FONT_NAME = "kalameh_medium";

    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface Default(Context context) {
        synchronized (cache) {
            if (!cache.containsKey(FONT_NAME)) {
                Typeface t = Typeface.createFromAsset(context.getAssets(), "font/" + FONT_NAME + ".ttf");
                cache.put(FONT_NAME, t);
                return t;
            }
            return cache.get(FONT_NAME);
        }
    }
}
