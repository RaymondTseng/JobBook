package com.example.jobbook.job.model;

import com.example.jobbook.bean.JobBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/6.
 */
public class JobModelImpl implements JobModel{


    @Override
    public void loadJobs(String url, OnLoadJobListListener listener) {

    }

    public interface OnLoadJobListListener {
        void onSuccess(List<JobBean> list);
        void onFailure(String msg, Exception e);
    }
}
