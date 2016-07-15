package com.example.jobbook.job.presenter;

import com.example.jobbook.bean.CompanyBean;
import com.example.jobbook.job.model.CompanyModel;
import com.example.jobbook.job.model.CompanyModelmpl;
import com.example.jobbook.job.view.CompanyView;

/**
 * Created by Xu on 2016/7/15.
 */
public class CompanyPresenterImpl implements CompanyPresenter, CompanyModelmpl.OnLoadCompanyListener {

    private CompanyModel mCompanyModel;
    private CompanyView mCompanyView;

    public CompanyPresenterImpl(CompanyView view) {
        mCompanyModel = new CompanyModelmpl();
        mCompanyView = view;
    }

    @Override
    public void loadCompany() {

    }

    @Override
    public void onSuccess(CompanyBean companyBean) {
        if(companyBean != null) {
            mCompanyView.showCompanyDetail("");
            mCompanyView.showCompanyIntroduction("");
        }
    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}