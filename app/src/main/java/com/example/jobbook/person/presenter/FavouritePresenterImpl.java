package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.person.model.FavouriteModel;
import com.example.jobbook.person.model.FavouriteModelImpl;
import com.example.jobbook.person.view.FavouriteView;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouritePresenterImpl implements FavouritePresenter, FavouriteModelImpl.OnLoadJobsListener {
    private FavouriteView mView;
    private FavouriteModel mModel;

    public FavouritePresenterImpl(FavouriteView mView) {
        this.mView = mView;
        mModel = new FavouriteModelImpl();
    }

    @Override
    public void loadFavouriteJobs(int pageIndex, String accountName) {
        mModel.loadFavouriteJobs(pageIndex, accountName, this);
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
