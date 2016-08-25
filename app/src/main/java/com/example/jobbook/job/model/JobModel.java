package com.example.jobbook.job.model;

import com.example.jobbook.job.model.JobModelImpl;

/**
 * Created by Xu on 2016/7/6.
 */
public interface JobModel {

    void loadJobs(JobModelImpl.OnLoadJobListListener listener);
}
