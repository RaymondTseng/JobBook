package com.example.jobbook.job.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.bean.JobDetailBean;
import com.example.jobbook.job.presenter.JobDetailPresenter;
import com.example.jobbook.job.presenter.JobDetailPresenterImpl;
import com.example.jobbook.job.view.JobDetailView;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by 椰树 on 2016/7/13.
 */
public class JobDetailActivity extends Activity implements View.OnClickListener, JobDetailView {
    private ImageButton mBackImageButton;
    private ImageButton mToCompanyDetailImageButton;
    private ImageButton mLikeImageButton;
    private LinearLayout mLoadingLinearLayout;
    private TextView mJobNameTextView;
    private TextView mJobLocationTextView;
    private TextView mSalaryTextView;
    private ImageView mCompanyImageView;
    private TextView mCompanyNameTextView;
    private TextView mCompanyLocationTextView;
    private TextView mCompanyDescriptionTextView;
    private TextView mJobDutyTextView;
    private TextView mJobRequireTextView;
    private FlexboxLayout mBenefitLayout;
    private JobDetailPresenter mPresenter;
    private RelativeLayout mSendCVLayout;
    private View view;
    private JobBean jobBean;
    private JobDetailBean jobDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        view = getWindow().getDecorView();
        jobDetailBean = new JobDetailBean();
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.job_detail_back_ib);
        mToCompanyDetailImageButton = (ImageButton) findViewById(R.id.job_detail_tocompany_ib);
        mLikeImageButton = (ImageButton) findViewById(R.id.job_detail_like_ib);
        mCompanyImageView = (ImageView) findViewById(R.id.job_detail_company_logo_iv);
        mJobNameTextView = (TextView) findViewById(R.id.job_detail_name_tv);
        mJobLocationTextView = (TextView) findViewById(R.id.job_detail_location_tv);
        mSalaryTextView = (TextView) findViewById(R.id.job_detail_salary_tv);
        mCompanyImageView = (ImageView) findViewById(R.id.job_detail_company_logo_iv);
        mCompanyNameTextView = (TextView) findViewById(R.id.job_detail_company_name_tv);
        mCompanyLocationTextView = (TextView) findViewById(R.id.job_detail_company_location_tv);
        mCompanyDescriptionTextView = (TextView) findViewById(R.id.job_detail_company_description_tv);
        mJobDutyTextView = (TextView) findViewById(R.id.job_detail_description_duty_content_tv);
        mJobRequireTextView = (TextView) findViewById(R.id.job_detail_description_require_content_tv);
        mBenefitLayout = (FlexboxLayout) findViewById(R.id.job_detail_benefit_ll);
        mSendCVLayout = (RelativeLayout) findViewById(R.id.job_detail_send_cv_ll);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        jobBean = (JobBean) getIntent().getExtras().getSerializable("job_detail");
        L.i("article_bean_activity", "123:" + jobBean.getId());
        mPresenter = new JobDetailPresenterImpl(this);
        mPresenter.loadJob(jobBean.getId());
        mToCompanyDetailImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mLikeImageButton.setOnClickListener(this);
        mSendCVLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.job_detail_back_ib:
                finish();
                break;
            case R.id.job_detail_tocompany_ib:
                Bundle bundle = new Bundle();
                bundle.putSerializable("company", jobDetailBean.getCompany());
                Util.toAnotherActivity(this, CompanyDetailActivity.class, bundle);
                break;
            case R.id.job_detail_like_ib:

                if (jobDetailBean.isIfLike() == 0) {
                    L.i("like_ib_click", "click like");
//                    mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
                    like(jobBean.getId());

                } else {
                    L.i("like_ib_click", "click unlike");
//                    mLikeImageButton.setImageResource(R.mipmap.favourite_white);
                    unlike(jobBean.getId());
                }
                refresh();
                break;
            case R.id.job_detail_send_cv_ll:
                sendCVCheckDialog();
                break;
        }
    }

    @Override
    public void like(String jobId) {
        mPresenter.like(jobId);
    }

    @Override
    public void unlike(String jobId) {
        mPresenter.unlike(jobId);
    }

    @Override
    public void switch2Chat() {

    }

    @Override
    public void addJob(JobDetailBean jobDetailBean) {
        this.jobDetailBean = jobDetailBean;
        L.i("addjob", "success");
        ImageLoadUtils.display(this, mCompanyImageView, jobDetailBean.getCompany().getLogo());
        mJobNameTextView.setText(jobDetailBean.getName());
        mJobLocationTextView.setText(jobDetailBean.getLocation());
        mSalaryTextView.setText(jobDetailBean.getSalary());
        mCompanyNameTextView.setText(jobDetailBean.getCompany().getName());
        mCompanyLocationTextView.setText(jobDetailBean.getCompany().getLocation());
        mCompanyDescriptionTextView.setText(jobDetailBean.getCompany().getScale());
        mJobDutyTextView.setText(jobDetailBean.getResponsibilities());
        mJobRequireTextView.setText(jobDetailBean.getRequirements());
        String[] welfare = jobDetailBean.getCompany().getWelfare().split("、");
        for (String temp : welfare) {
            TextView textView = new TextView(this);
            textView.setText(temp);
            textView.setPadding(6, 6, 6, 6);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.job_detail_benefit_bg));
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
            textView.setTextSize(12);
            mBenefitLayout.addView(textView);
        }
        if (MyApplication.getmLoginStatus() == 1) {
            L.i("like_status", jobDetailBean.isIfLike() + "");
            if (jobDetailBean.isIfLike() == 0) {
                mLikeImageButton.setImageResource(R.mipmap.favourite_white);
            } else {
                mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
            }
        }
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {
        Util.showSnackBar(this, "岗位详情读取错误，请重试！");
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void NoLoginError() {
        Util.showSnackBar(this, "请先登录");
    }

    @Override
    public void likeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
        Util.showSnackBar(this, "收藏成功！");
    }

    @Override
    public void unlikeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_white);
        Util.showSnackBar(this, "取消收藏成功！");
    }

    @Override
    public void likeError() {
        Util.showSnackBar(this, "收藏失败，请重试！");
    }

    @Override
    public void unlikeError() {
        Util.showSnackBar(this, "取消收藏失败，请重试！");
    }

    @Override
    public void sendCV(String companyId) {
        mPresenter.sendCV(companyId);
    }

    @Override
    public void sendCVSuccess() {
        Util.showSnackBar(this, "提交成功！");
    }

    @Override
    public void sendCVFailure() {
        Util.showSnackBar(this, "提交失败！");
    }

    @Override
    public void sendCVEmailFailed() {
        Util.showSnackBar(this, "邮箱发送失败");
    }

    @Override
    public void sendCVNoDestination() {
        Util.showSnackBar(this, "该公司没有官方邮箱，请重试！");
    }

    @Override
    public void sendCVRepeated() {
        Util.showSnackBar(this, "您之前已经发送过！");
    }

    private void refresh() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(null);
    }

    private void sendCVCheckDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.width = Util.dip2px(this, 280);
//        p.height = Util.dip2px(this, 109);
        p.width = Util.dip2px(this, 300);
        p.height = Util.dip2px(this, 140);
        window.setAttributes(p);
        window.setContentView(R.layout.send_cv_check_layout);
        TextView mSureTextView = (TextView) window.findViewById(R.id.send_cv_sure_tv);
        TextView mCancelTextView = (TextView) window.findViewById(R.id.send_cv_cancel_tv);
        mSureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCV(jobDetailBean.getCompany().getId());
                alertDialog.dismiss();
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
