package com.example.jobbook.job.presenter;

/**
 * Created by Xu on 2016/7/5.
 */
public interface JobPresenter {

    void loadJobs(int pageIndex, boolean isRecommend, String type, String location);

    void search();

}