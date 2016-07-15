package com.example.jobbook.job.model;

/**
 * Created by Xu on 2016/7/15.
 */
public interface CompanyModel {

    void loadCompany(String url, CompanyModelmpl.OnLoadCompanyListener listener);

}
