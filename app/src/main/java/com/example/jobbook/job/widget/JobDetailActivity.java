package com.example.jobbook.job.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
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
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/13.
 */
public class JobDetailActivity extends Activity implements View.OnClickListener, JobDetailView {
    private ImageButton mBackImageButton;
    private TextView mToCompanyDetailTextView;
    private ImageButton mLikeImageButton;
    private LinearLayout mLoadingLinearLayout;
    private TextView mJobNameTextView;
    private TextView mJobLocationTextView;
    private TextView mSalaryTextView;
    //    private ImageView mCompanyImageView;
    private TextView mCompanyNameTextView;
    private TextView mCompanyLocationTextView;
    private TextView mCompanyDescriptionTextView;
    private TextView mJobDutyTextView;
    private TextView mJobRequireTextView;
    //    private FlexboxLayout mBenefitLayout;
    private TextView mBenefitTextView;
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
        mToCompanyDetailTextView = (TextView) findViewById(R.id.job_detail_tocompany_tv);
        mLikeImageButton = (ImageButton) findViewById(R.id.job_detail_like_ib);
//        mCompanyImageView = (ImageView) findViewById(R.id.job_detail_company_logo_iv);
        mJobNameTextView = (TextView) findViewById(R.id.job_detail_name_tv);
        mJobLocationTextView = (TextView) findViewById(R.id.job_detail_location_tv);
        mSalaryTextView = (TextView) findViewById(R.id.job_detail_salary_tv);
        mCompanyNameTextView = (TextView) findViewById(R.id.job_detail_company_name_tv);
        mCompanyLocationTextView = (TextView) findViewById(R.id.job_detail_company_location_tv);
        mCompanyDescriptionTextView = (TextView) findViewById(R.id.job_detail_company_description_tv);
        mJobDutyTextView = (TextView) findViewById(R.id.job_detail_description_duty_content_tv);
        mJobRequireTextView = (TextView) findViewById(R.id.job_detail_description_require_content_tv);
        mBenefitTextView = (TextView) findViewById(R.id.job_detail_benefit_tv);
        mSendCVLayout = (RelativeLayout) findViewById(R.id.job_detail_send_cv_ll);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        jobBean = (JobBean) getIntent().getExtras().getSerializable("job_detail");
        L.i("article_bean_activity", "123:" + jobBean.getId());
        mPresenter = new JobDetailPresenterImpl(this);
        mPresenter.loadJob(jobBean.getId());
        mToCompanyDetailTextView.setOnClickListener(this);
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
            case R.id.job_detail_tocompany_tv:
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("company", jobDetailBean.getCompany());
//                Util.toAnotherActivity(this, CompanyDetailActivity.class, bundle);
//                Toast.makeText(this, "website:" + jobDetailBean.getCompany().getWebsite(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(jobDetailBean.getCompany().getWebsite()));
                startActivity(intent);
                break;
            case R.id.job_detail_like_ib:

                if (jobDetailBean.isIfLike() == 0) {
                    L.i("like_ib_click", "click like");
//                    mLikeImageButton.setImageResource(R.mipmap.favourite);
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
//        ImageLoadUtils.display(this, mCompanyImageView, jobDetailBean.getCompany().getLogo());
        mJobNameTextView.setText(jobDetailBean.getName());
        mJobLocationTextView.setText(jobDetailBean.getLocation());
        mSalaryTextView.setText(jobDetailBean.getSalary());
        mCompanyNameTextView.setText(jobDetailBean.getCompany().getName());
        mCompanyLocationTextView.setText(jobDetailBean.getCompany().getLocation());
        mCompanyDescriptionTextView.setText(jobDetailBean.getCompany().getScale());
        mJobDutyTextView.setText(jobDetailBean.getResponsibilities());
        mJobRequireTextView.setText(jobDetailBean.getRequirements());
        String welfare = jobDetailBean.getCompany().getWelfare().replace(",", " • ");
        mBenefitTextView.setText(welfare);
        if (MyApplication.getmLoginStatus() == 1) {
            L.i("like_status", jobDetailBean.isIfLike() + "");
            if (jobDetailBean.isIfLike() == 0) {
                mLikeImageButton.setImageResource(R.mipmap.favourite_white);
            } else {
                mLikeImageButton.setImageResource(R.mipmap.favourite);
            }
        }
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {
        Util.showSnackBar(view, "岗位详情读取错误，请重试！");
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void NoLoginError() {
        Util.showSnackBar(view, "请先登录");
    }

    @Override
    public void likeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite);
        Util.showSnackBar(view, "收藏成功！");
    }

    @Override
    public void unlikeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_white);
        Util.showSnackBar(view, "取消收藏成功！");
    }

    @Override
    public void likeError() {
        Util.showSnackBar(view, "收藏失败，请重试！");
    }

    @Override
    public void unlikeError() {
        Util.showSnackBar(view, "取消收藏失败，请重试！");
    }

    @Override
    public void sendCV(String companyId) {
        mPresenter.sendCV(companyId);
    }

    @Override
    public void sendCVSuccess() {
        Util.showSnackBar(view, "提交成功！");
    }

    @Override
    public void sendCVFailure() {
        Util.showSnackBar(view, "提交失败！");
    }

    @Override
    public void sendCVEmailFailed() {
        Util.showSnackBar(view, "邮箱发送失败");
    }

    @Override
    public void sendCVNoDestination() {
        Util.showSnackBar(view, "该公司没有官方邮箱，请重试！");
    }

    @Override
    public void sendCVRepeated() {
        Util.showSnackBar(view, "您之前已经发送过！");
    }

    @Override
    public void sendCVNoWrite() {
        Util.showSnackBar(view, "您还未填写简历！");
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
