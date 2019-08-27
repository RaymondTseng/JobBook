package com.example.jobbook.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.jobbook.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Xu on 2018/2/20.
 */

public class SnackBarUtil {

    /**
     * 显示弹出窗口
     *
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content) {
//        View view = activity.getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView) demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackBar(Activity activity, String content) {
//        View view = activity.getWindow().getDecorView();
        View view = activity.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView) demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * 显示弹出窗口
     *
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content, String buttonText) {
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView) demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        //R.color.colorBlue
        snackbar.setActionTextColor(Color.parseColor("#457ff4"));
        snackbar.setAction(buttonText, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    /**
     * 显示弹出窗口
     *
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content, String buttonText, View.OnClickListener listener) {
//        View view = activity.getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView) demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        //R.color.colorBlue
        snackbar.setActionTextColor(Color.parseColor("#457ff4"));
        snackbar.setAction(buttonText, listener);
        snackbar.show();
    }

    public static void showSnackBar(Activity activity, String content, String buttonText, View.OnClickListener listener) {
        View view = activity.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView) demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        //R.color.colorBlue
        snackbar.setActionTextColor(Color.parseColor("#457ff4"));
        snackbar.setAction(buttonText, listener);
        snackbar.show();
    }

}
