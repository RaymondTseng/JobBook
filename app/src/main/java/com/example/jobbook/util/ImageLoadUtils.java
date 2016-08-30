package com.example.jobbook.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Xu on 2016/8/29.
 */
public class ImageLoadUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }
}
