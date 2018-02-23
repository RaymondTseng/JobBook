package com.example.jobbook.presenter.person;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.FavouriteJobContract;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

public class FavouriteJobPresenter extends RxPresenter<FavouriteJobContract.View> implements FavouriteJobContract.Presenter {

    public FavouriteJobPresenter(FavouriteJobContract.View view) {
        attach(view);
    }

    @Override
    public void loadFavouriteJobs(String accountName) {
        addSubscribe(RetrofitService.loadFavouriteJobs(accountName)
                .subscribeWith(new BaseSubscriber<List<JobBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<JobBean> jobBeans) {
                        mView.loadJobs(jobBeans);
                    }
                }));
    }
}
