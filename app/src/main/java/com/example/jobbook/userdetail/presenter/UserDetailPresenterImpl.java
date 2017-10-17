package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.userdetail.model.UserDetailModel;
import com.example.jobbook.userdetail.model.UserDetailModelImpl;
import com.example.jobbook.userdetail.view.UserDetailView;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by root on 16-12-5.
 */

public class UserDetailPresenterImpl implements UserDetailPresenter, UserDetailModelImpl.OnFollowListener,
        UserDetailModelImpl.OnLoadUserDetailByAccountListener, UserDetailModelImpl.OnUnFollowListener,
        UserDetailModelImpl.OnRefreshListener {
    private UserDetailModel mModel;
    private UserDetailView mView;

    public UserDetailPresenterImpl(UserDetailView mView) {
        this.mView = mView;
        mModel = new UserDetailModelImpl();
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        mView.showProgress();
        mModel.follow(myAccount, hisAccount, this);
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
        mView.showProgress();
        mModel.unFollow(myAccount, hisAccount, this);
    }

    @Override
    public void refreshPersonBean() {
        mModel.refreshPersonBean(this);
    }


    @Override
    public void onSuccess() {
        mView.hideProgress();
        mView.followSuccess();
    }

    @Override
    public void onSuccess(TypePersonBean personBean) {

    }

    @Override
    public void onLoadFailure(String msg, Exception e) {

    }

    @Override
    public void onUnfollowSuccess() {
        mView.hideProgress();
        mView.unfollowSuccess();
    }

    @Override
    public void onRefreshSuccess(TypePersonBean personBean) {
        mView.onRefreshSuccess(personBean);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.onFail(msg);
    }

}
