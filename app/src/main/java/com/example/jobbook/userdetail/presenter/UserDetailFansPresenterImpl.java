package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailFansView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansPresenterImpl implements UserDetailFansPresenter {
    private UserDetailFansView mView;

    public UserDetailFansPresenterImpl(UserDetailFansView mView){
        this.mView = mView;
    }
    @Override
    public void loadFans(String account) {
        RetrofitService.loadFanList(account, "")
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<TypePersonBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.onError(throwable.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(List<TypePersonBean> list) {
                        mView.hideProgress();
                        mView.loadFans(list);
                    }
                });
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        RetrofitService.follow(myAccount, hisAccount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.onError(throwable.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(String s) {
                        mView.followSuccess();
                    }
                });
    }

}
