package com.example.jobbook.job.model;

/**
 * Created by Xu on 2016/7/6.
 */
public interface JobModel {

    void loadJobs(int pageIndex, boolean isRecommend, String type, String location, JobModelImpl.OnLoadJobListListener listener);
}
