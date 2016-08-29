package com.example.jobbook.job.view;

import com.example.jobbook.bean.CompanyBean;

/**
 * Created by Xu on 2016/7/15.
 */
public interface CompanyView {

    void addCompany(CompanyBean companyBean);

    void showProgress();

    void hideProgress();

    void showLoadFailMsg();
}
