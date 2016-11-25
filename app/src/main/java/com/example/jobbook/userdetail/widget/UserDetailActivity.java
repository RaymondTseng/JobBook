package com.example.jobbook.userdetail.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jobbook.R;

/**
 * Created by root on 16-11-21.
 */

public class UserDetailActivity extends Activity{
    private ImageView mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }

}
