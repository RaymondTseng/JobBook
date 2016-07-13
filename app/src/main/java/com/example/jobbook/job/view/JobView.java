package com.example.jobbook.job.view;

import com.example.jobbook.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface JobView {

    void showProgress();

    void addJobs(List<JobBean> jobList);

    void hideProgress();

    void showLoadingFailMsg();

}
