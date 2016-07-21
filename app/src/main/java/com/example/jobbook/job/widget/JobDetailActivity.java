package com.example.jobbook.job.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.jobbook.R;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/13.
 */
public class JobDetailActivity extends Activity implements View.OnClickListener{
    private ImageButton mBackImageButton;
    private ImageButton mToCompanyDetailImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.job_detail_back_ib);
        mToCompanyDetailImageButton = (ImageButton) findViewById(R.id.job_detail_tocompany_ib);
        mToCompanyDetailImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.job_detail_back_ib:
                finish();
                break;
            case R.id.job_detail_tocompany_ib:
                Util.toAnotherActivity(this, CompanyDetailActivity.class);
                break;
        }
    }
}
