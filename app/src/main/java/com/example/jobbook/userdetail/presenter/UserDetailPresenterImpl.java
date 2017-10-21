package com.example.jobbook.userdetail.presenter;

import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailView;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by root on 16-12-5.
 */

public class UserDetailPresenterImpl implements UserDetailPresenter {
    private UserDetailView mView;

    public UserDetailPresenterImpl(UserDetailView mView) {
        this.mView = mView;
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
                        mView.onFail(throwable.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(String s) {
                        mView.followSuccess();
                    }
                });
    }

    @Override
    public void loadUserDetailByAccount(String account) {
        RetrofitService.loadUserDetailByAccount(account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<TypePersonBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.onFail(e.getMessage());
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.hideProgress();
                        mView.loadSuccess(typePersonBean);
                    }
                });
    }

    @Override
    public void unfollow(String myAccount, String hisAccount) {
        RetrofitService.unfollow(myAccount, hisAccount)
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
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.onFail(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        mView.hideProgress();
                        mView.unfollowSuccess();
                    }
                });
    }

    @Override
    public void refreshPersonBean() {
        String account = MyApplication.getAccount();
        if (TextUtils.isEmpty(account)) {
            return;
        }
        RetrofitService.loadUserDetailByAccount(account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<TypePersonBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.onFail(e.getMessage());
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.hideProgress();
                        mView.onRefreshSuccess(typePersonBean);
                    }
                });
    }

}
