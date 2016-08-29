package com.example.jobbook.job.widget;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.bean.CompanyBean;
import com.example.jobbook.bean.CompanyCommentBean;
import com.example.jobbook.job.CompanyCommentListViewAdapter;
import com.example.jobbook.job.presenter.CompanyPresenter;
import com.example.jobbook.job.presenter.CompanyPresenterImpl;
import com.example.jobbook.job.view.CompanyView;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/15.
 */
public class CompanyDetailActivity extends Activity implements CompanyView, View.OnClickListener, CompanyCommentListViewAdapter.OnItemClickListener {

    private CompanyPresenter mCompanyPresenter;
    private ImageView mCompanyLogoImageView;
    private TextView mCompanyNameTextView;
    private TextView mCompanyLocationTextView;
    private TextView mCompanyDescriptionTextView;
    private TextView mCompanyIntroductionTextView;
    private ListView mCompanyCommentListView;
    private CompanyCommentListViewAdapter mAdapter;
    private ImageButton mBackImageButton;
    private FlexboxLayout mBenefitLayout;
    private List<CompanyCommentBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        initViews();
        initEvents();
    }

    private void initViews() {
        mCompanyLogoImageView = (ImageView) findViewById(R.id.compamy_detail_company_logo_iv);
        mCompanyNameTextView = (TextView) findViewById(R.id.compamy_detail_company_name_tv);
        mCompanyLocationTextView = (TextView) findViewById(R.id.compamy_detail_company_location_tv);
        mCompanyDescriptionTextView = (TextView) findViewById(R.id.compamy_detail_company_description_tv);
        mCompanyIntroductionTextView = (TextView) findViewById(R.id.company_detail_company_introduction_tv);
        mCompanyCommentListView = (ListView) findViewById(R.id.company_detail_lv);
        mBackImageButton = (ImageButton) findViewById(R.id.company_detail_back_ib);
        mBenefitLayout = (FlexboxLayout) findViewById(R.id.compamy_detail_benefit_ll);
    }

    private void initEvents(){
        list = new ArrayList<>();
        CompanyBean companyBean = (CompanyBean) getIntent().getExtras().getSerializable("company");
        mCompanyPresenter = new CompanyPresenterImpl(this);
        mCompanyPresenter.loadCompany(companyBean);
        mBackImageButton.setOnClickListener(this);
        mAdapter = new CompanyCommentListViewAdapter(this);
        mCompanyCommentListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.updateData(list);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.company_detail_back_ib:
                finish();
                break;
        }
    }

    @Override
    public void addCompany(CompanyBean companyBean) {
        mCompanyNameTextView.setText(companyBean.getName());
        mCompanyLocationTextView.setText(companyBean.getLocation());
        mCompanyDescriptionTextView.setText(companyBean.getScale());
        mCompanyIntroductionTextView.setText(companyBean.getIntroduction());
        if(companyBean.getComments().size() != 0){
            mAdapter.updateData(companyBean.getComments());
        }
        String[] welfare = companyBean.getWelfare().split("、");
        for(String temp : welfare){
            TextView textView = new TextView(this);
            textView.setText(temp);
            textView.setPadding(6,6,6,6);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.job_detail_benefit_bg));
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
            textView.setTextSize(12);
            mBenefitLayout.addView(textView);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
