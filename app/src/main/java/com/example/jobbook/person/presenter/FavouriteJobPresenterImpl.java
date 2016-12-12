package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.person.model.FavouriteJobModel;
import com.example.jobbook.person.model.FavouriteJobModelImpl;
import com.example.jobbook.person.view.FavouriteJobView;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouriteJobPresenterImpl implements FavouriteJobPresenter, FavouriteJobModelImpl.OnLoadJobsListener {
    private FavouriteJobView mView;
    private FavouriteJobModel mModel;

    public FavouriteJobPresenterImpl(FavouriteJobView mView) {
        this.mView = mView;
        mModel = new FavouriteJobModelImpl();
    }

    @Override
    public void loadFavouriteJobs(String accountName) {
        mView.showProgress();
        mModel.loadFavouriteJobs(accountName, this);
    }

    @Override
    public void onSuccess(List<JobBean> mJobs) {
        mView.hideProgress();
        mView.addJobs(mJobs);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.showLoadFailMsg();
    }
}
