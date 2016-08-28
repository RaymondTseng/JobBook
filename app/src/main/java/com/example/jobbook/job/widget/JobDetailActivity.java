package com.example.jobbook.job.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;
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
    private TextView mJobNameTextView;
    private TextView mJobLocationTextView;
    private TextView mSalaryTextView;
    private ImageView mCompanyImageView;
    private TextView mCompanyNameTextView;
    private TextView mCompanyLocationTextView;
    private TextView mCompanyDescriptionTextView;
    private TextView mJobDutyTextView;
    private TextView mJobRequireTextView;
    private JobDetailPresenter mPresenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.job_detail_back_ib);
        mToCompanyDetailImageButton = (ImageButton) findViewById(R.id.job_detail_tocompany_ib);
        mJobNameTextView = (TextView) findViewById(R.id.job_detail_name_tv);
        mJobLocationTextView = (TextView) findViewById(R.id.job_detail_location_tv);
        mSalaryTextView = (TextView) findViewById(R.id.job_detail_salary_tv);
        mCompanyImageView = (ImageView) findViewById(R.id.job_detail_company_logo_iv);
        mCompanyNameTextView = (TextView) findViewById(R.id.job_detail_company_name_tv);
        mCompanyLocationTextView = (TextView) findViewById(R.id.job_detail_company_location_tv);
        mCompanyDescriptionTextView = (TextView) findViewById(R.id.job_detail_company_description_tv);
        mJobDutyTextView = (TextView) findViewById(R.id.job_detail_description_duty_content_tv);
        mJobRequireTextView = (TextView) findViewById(R.id.job_detail_description_require_content_tv);
    }

    private void initEvents(){
        JobBean jobBean = (JobBean) getIntent().getExtras().getSerializable("job_detail");
        Log.i("article_bean_activity", "123:" + jobBean.getId());
        mPresenter = new JobDetailPresenterImpl(this);
        mPresenter.loadJob(jobBean.getId());
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
            case R.id.job_detail_like_ib:
                break;
        }
    }

    @Override
    public void like(String jobId) {
        mPresenter.like(jobId);
    }

    @Override
    public void switch2Chat() {

    }

    @Override
    public void submitCV() {

    }

    @Override
    public void addJob(JobDetailBean jobDetailBean) {
        mJobNameTextView.setText(jobDetailBean.getName());
        mJobLocationTextView.setText(jobDetailBean.getLocation());
        mSalaryTextView.setText(jobDetailBean.getSalary());
        mCompanyNameTextView.setText(jobDetailBean.getCompany().getName());
        mCompanyLocationTextView.setText(jobDetailBean.getCompany().getLocation());
        mCompanyDescriptionTextView.setText(jobDetailBean.getCompany().getScale());
        mJobDutyTextView.setText(jobDetailBean.getResponsibilities());
        mJobRequireTextView.setText(jobDetailBean.getRequirements());

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {
        final Snackbar snackbar = Snackbar.make(view, "岗位详情读取错误，请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void showProhress() {

    }
}
