package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailMomentView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailMomentPresenterImpl implements UserDetailMomentPresenter {
    private UserDetailMomentView mView;

    public UserDetailMomentPresenterImpl(UserDetailMomentView mView) {
        this.mView = mView;
    }

    @Override
    public void loadMoments(String hisAccount, String myAccount) {
        RetrofitService.loadMomentList(hisAccount, myAccount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MomentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(List<MomentBean> list) {
                        mView.hideProgress();
                        mView.loadMoments(list);
                    }
                });
    }

}
