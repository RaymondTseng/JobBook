package com.example.jobbook.person.model;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface FavouriteModel {
    void loadJobs(String url, FavouriteModelImpl.OnLoadJobsListener mListener);
}
