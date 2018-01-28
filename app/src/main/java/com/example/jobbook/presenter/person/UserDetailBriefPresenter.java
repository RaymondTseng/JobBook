package com.example.jobbook.presenter.person;

import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UserDetaiBriefContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by Xu on 2018/1/28.
 */

public class UserDetailBriefPresenter extends RxPresenter<UserDetaiBriefContract.View> implements UserDetaiBriefContract.Presenter {

    public UserDetailBriefPresenter(UserDetaiBriefContract.View view) {
        attach(view);
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
