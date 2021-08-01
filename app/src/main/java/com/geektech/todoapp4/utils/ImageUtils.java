package com.geektech.todoapp4.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static void loadImage(ImageView imageView, Uri uri ){
        Glide.with(imageView.getContext()).load(uri).into(imageView);
    }

}
