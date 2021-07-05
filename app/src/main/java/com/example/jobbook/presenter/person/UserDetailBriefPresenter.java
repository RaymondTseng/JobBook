package com.example.jobbook.presenter.person;

import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UserDetailBriefContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by Xu on 2018/1/28.
 */

public class UserDetailBriefPresenter extends RxPresenter<UserDetailBriefContract.View> implements UserDetailBriefContract.Presenter {

    public UserDetailBriefPresenter(UserDetailBriefContract.View view) {
        attach(view);
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        addSubscribe(RetrofitService.follow(myAccount, hisAccount)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.followSuccess();
                    }
                }));
    }

    @Override
    public void loadUserDetailByAccount(String account) {
        addSubscribe(RetrofitService.loadUserDetailByAccount(account)
                .subscribeWith(new BaseSubscriber<TypePersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.loadSuccess(typePersonBean);
                    }
                }));
    }

    @Override
    public void unfollow(String myAccount, String hisAccount) {
        addSubscribe(RetrofitService.unfollow(myAccount, hisAccount)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.unfollowSuccess();
                    }
                }));
    }

    @Override
    public void refreshPersonBean() {
        String account = MyApplication.getAccount();
        if (TextUtils.isEmpty(account)) {
            return;
        }
        addSubscribe(RetrofitService.loadUserDetailByAccount(account)
                .subscribeWith(new BaseSubscriber<TypePersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(TypePersonBean typePersonBean) {
                        mView.onRefreshSuccess(typePersonBean);
                    }
                }));
    }
}
