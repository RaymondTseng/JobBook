package com.example.jobbook.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Xu on 2016/8/29.
 */
public class ImageLoadUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
//        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
//                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
//        Log.i("image_url", url);
//        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).crossFade(100).into(imageView);
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(imageView);
    }

}
