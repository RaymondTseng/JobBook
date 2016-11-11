package com.example.jobbook.util;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Xu on 2016/10/27.
 */

public class AffectUtil {

    public static View.OnFocusChangeListener changeHintColorListener(final EditText editText) {
        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHintTextColor(Color.parseColor("#18000000"));
                } else {
                    editText.setHintTextColor(Color.parseColor("#61000000"));
                }
            }
        };
        return listener;
    }
}
