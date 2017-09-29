package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.JobBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.FavouriteJobView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<JobBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(List<JobBean> jobs) {
                        mView.hideProgress();
                        mView.addJobs(jobs);
                    }
                });
    }

}
