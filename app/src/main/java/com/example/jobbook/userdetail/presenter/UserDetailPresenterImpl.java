package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.userdetail.model.UserDetailModel;
import com.example.jobbook.userdetail.model.UserDetailModelImpl;
import com.example.jobbook.userdetail.view.UserDetailView;

/**
 * Created by root on 16-12-5.
 */

public class UserDetailPresenterImpl implements UserDetailPresenter, UserDetailModelImpl.OnFollowListener, UserDetailModelImpl.OnLoadUserDetailByAccountListener{
    private UserDetailModel mModel;
    private UserDetailView mView;

    public UserDetailPresenterImpl(UserDetailView mView){
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
        mView.showProgress();
        mModel.loadUserDetailByAccount(account, this);
    }

    @Override
    public void onSuccess() {
        mView.hideProgress();
        mView.followSuccess();
    }

    @Override
    public void onSuccess(PersonBean personBean) {
        mView.hideProgress();
        mView.loadSuccess(personBean);
    }

    @Override
    public void onLoadFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.loadFail();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.followFail();
    }

}
