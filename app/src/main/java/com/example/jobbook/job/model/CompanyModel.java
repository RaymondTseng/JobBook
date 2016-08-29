package com.example.jobbook.job.model;

import com.example.jobbook.bean.CompanyBean;

/**
 * Created by Xu on 2016/7/15.
 */
public interface CompanyModel {

    void loadCompany(CompanyBean companyBean, CompanyModelmpl.OnLoadCompanyListener listener);

}
