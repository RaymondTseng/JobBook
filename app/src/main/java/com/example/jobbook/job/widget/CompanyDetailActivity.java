package com.example.jobbook.job.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.job.presenter.CompanyPresenter;
import com.example.jobbook.job.presenter.CompanyPresenterImpl;
import com.example.jobbook.job.view.CompanyView;

/**
 * Created by Xu on 2016/7/15.
 */
public class CompanyDetailActivity extends Activity implements CompanyView, View.OnClickListener {

    private CompanyPresenter mCompanyPresenter;
    private ImageView mCompanyLogoImageView;
    private TextView mCompanyNameTextView;
    private TextView mCompanyLocationTextView;
    private TextView mCompanyDescriptionTextView;
    private TextView mCompanyIntroductionTextView;
    private ListView mCompanyCommentListView;
    private ImageButton mBackImageButton;

    public CompanyDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        mCompanyPresenter = new CompanyPresenterImpl(this);
        initViews();
    }

    private void initViews() {
        mCompanyLogoImageView = (ImageView) findViewById(R.id.compamy_detail_company_logo_iv);
        mCompanyNameTextView = (TextView) findViewById(R.id.compamy_detail_company_name_tv);
        mCompanyLocationTextView = (TextView) findViewById(R.id.compamy_detail_company_location_tv);
        mCompanyDescriptionTextView = (TextView) findViewById(R.id.company_detail_description_tv);
        mCompanyIntroductionTextView = (TextView) findViewById(R.id.company_detail_company_introduction_tv);
        mCompanyCommentListView = (ListView) findViewById(R.id.company_detail_lv);
        mBackImageButton = (ImageButton) findViewById(R.id.company_detail_back_ib);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void showCompanyDetail(String companyDetail) {

    }

    @Override
    public void showCompanyIntroduction(String companyIntroduction) {

    }

    @Override
    public void showCompanyComment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.company_detail_back_ib:
                finish();
                break;
        }
    }
}
