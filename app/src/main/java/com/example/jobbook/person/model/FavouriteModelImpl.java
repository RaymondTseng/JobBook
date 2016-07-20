package com.example.jobbook.person.model;

import com.example.jobbook.bean.JobBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouriteModelImpl implements FavouriteModel {
    @Override
    public void loadJobs(String url, OnLoadJobsListener mListener) {
        List<JobBean> mJobs = new ArrayList<>();
        mListener.onSuccess(mJobs);
    }
    public interface OnLoadJobsListener{
        void onSuccess(List<JobBean> mJobs);
        void onFailure(String msg, Exception e);
    }
}
