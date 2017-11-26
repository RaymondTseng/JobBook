package com.example.jobbook.person.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
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
                .subscribe(new BaseSubscriber<List<JobBean>>() {
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
