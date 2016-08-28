package com.example.jobbook.job.view;

import com.example.jobbook.bean.JobBean;

/**
 * Created by 椰树 on 2016/8/28.
 */
public interface JobDetailView {
    void like();

    void switch2Chat();

    void submitCV();

    void addJob(JobBean jobBean);

    void hideProgress();

    void showLoadFailMsg();

    void showProhress();
}
