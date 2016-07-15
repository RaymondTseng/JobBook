package com.example.jobbook.job.model;

import com.example.jobbook.bean.CompanyBean;

/**
 * Created by Xu on 2016/7/15.
 */
public class CompanyModelmpl implements CompanyModel {

    @Override
    public void loadCompany(String url, OnLoadCompanyListener listener) {

    }

    public interface OnLoadCompanyListener {
        void onSuccess(CompanyBean companyBean);
        void onFailure(String msg, Exception e);
    }
}
