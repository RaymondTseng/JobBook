package com.example.jobbook.job.widget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.job.presenter.JobDetailPresenter;
import com.example.jobbook.job.presenter.JobDetailPresenterImpl;
import com.example.jobbook.job.view.JobDetailView;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/13.
 */
public class JobDetailActivity extends Activity implements View.OnClickListener, JobDetailView{
    private ImageButton mBackImageButton;
    private ImageButton mToCompanyDetailImageButton;
    private JobDetailPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.job_detail_back_ib);
        mToCompanyDetailImageButton = (ImageButton) findViewById(R.id.job_detail_tocompany_ib);
    }

    private void initEvents(){
        JobBean jobBean = (JobBean) getIntent().getExtras().getSerializable("job_detail");
        Log.i("article_bean_activity", "123:" + jobBean.getId());
        mPresenter = new JobDetailPresenterImpl(this);
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

    @Override
    public void like() {

    }

    @Override
    public void switch2Chat() {

    }

    @Override
    public void submitCV() {

    }

    @Override
    public void addJob(JobBean jobBean) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void showProhress() {

    }
}
