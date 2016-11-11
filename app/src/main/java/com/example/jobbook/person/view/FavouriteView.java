package com.example.jobbook.person.view;

import com.example.jobbook.bean.JobBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface FavouriteView {

    void showProgress();

    void addJobs(List<JobBean> mJobs);

    void hideProgress();

    void showLoadFailMsg();

}
