package com.example.jobbook.person.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.jobbook.R;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageButton mBackImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.setting_back_ib);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back_ib:
                finish();
                break;
        }
    }
}
