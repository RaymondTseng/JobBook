package com.example.jobbook.person.model;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface FavouriteJobModel {

    void loadFavouriteJobs(String accountName, FavouriteJobModelImpl.OnLoadJobsListener mListener);
}
