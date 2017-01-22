package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.userdetail.model.UserDetailFansModel;
import com.example.jobbook.userdetail.model.UserDetailFansModelImpl;
import com.example.jobbook.userdetail.view.UserDetailFansView;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansPresenterImpl implements UserDetailFansPresenter,
        UserDetailFansModelImpl.OnLoadFansListener, UserDetailFansModelImpl.OnFollowListener {
    private UserDetailFansModel mModel;
    private UserDetailFansView mView;

    public UserDetailFansPresenterImpl(UserDetailFansView mView){
        this.mView = mView;
        this.mModel = new UserDetailFansModelImpl();
    }
    @Override
    public void loadFans(String account) {
        mView.showProgress();
        mModel.loadFans(account, this);
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        mView.showProgress();
        mModel.follow(myAccount, hisAccount, this);
    }

    @Override
    public void onSuccess(List<TypePersonBean> mFans) {
        mView.hideProgress();
        mView.loadFans(mFans);
    }

    @Override
    public void onSuccess() {
        mView.followSuccess();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.onError(msg);
        mView.hideProgress();
    }
}
