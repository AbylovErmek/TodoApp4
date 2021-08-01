package com.geektech.todoapp4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URI;


public class Prefs {

    private SharedPreferences preferences;
    private static Prefs instance;

    public static Prefs getInstance() {
        return instance;
    }

    public Prefs(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isBoardShown() {
        return preferences.getBoolean("isShown", false);
    }

    public void uriImage(String uri) {
        preferences.edit().putString("uri", uri).apply();
    }

    public String uriImage() {
        return preferences.getString("uri", null);
    }
}
