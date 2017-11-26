package com.example.jobbook.userdetail.presenter;

import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailView;

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
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
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
                .subscribe(new BaseSubscriber<TypePersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.loadSuccess(typePersonBean);
                    }
                });
    }

    @Override
    public void unfollow(String myAccount, String hisAccount) {
        RetrofitService.unfollow(myAccount, hisAccount)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
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
                .subscribe(new BaseSubscriber<TypePersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.onRefreshSuccess(typePersonBean);
                    }
                });
    }

}
