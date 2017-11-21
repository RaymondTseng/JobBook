package com.example.jobbook.person.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.JobBean;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.FavouriteJobView;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class FavouriteJobPresenterImpl implements FavouriteJobPresenter {
    private FavouriteJobView mView;

    public FavouriteJobPresenterImpl(FavouriteJobView mView) {
        this.mView = mView;
    }

    @Override
    public void loadFavouriteJobs(String accountName) {
        RetrofitService.loadFavouriteJobs(accountName)
                .subscribe(new BaseObserver<List<JobBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<JobBean> jobBeans) {
                        mView.addJobs(jobBeans);
                    }
                });
    }

}
