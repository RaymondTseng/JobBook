package com.example.jobbook.person.model;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface FavouriteModel {

    void loadFavouriteJobs(int pageIndex, String accountName, FavouriteModelImpl.OnLoadJobsListener mListener);
}
