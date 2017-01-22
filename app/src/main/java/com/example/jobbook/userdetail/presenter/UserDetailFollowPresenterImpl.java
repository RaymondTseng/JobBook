package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.userdetail.model.UserDetailFollowModel;
import com.example.jobbook.userdetail.model.UserDetailFollowModelImpl;
import com.example.jobbook.userdetail.view.UserDetailFollowView;
import com.example.jobbook.util.Util;

import java.util.List;


/**
 * Created by root on 16-11-30.
 */

public class UserDetailFollowPresenterImpl implements UserDetailFollowPresenter,
        UserDetailFollowModelImpl.OnLoadFollowListener, UserDetailFollowModelImpl.OnFollowListener {
    private UserDetailFollowView mView;
    private UserDetailFollowModel mModel;

    public UserDetailFollowPresenterImpl(UserDetailFollowView mView){
        this.mView = mView;
        this.mModel = new UserDetailFollowModelImpl();
    }

    @Override
    public void loadFollow(String account) {
        mView.showProgress();
        mModel.loadFollow(account, this);
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        mView.showProgress();
        mModel.follow(myAccount, hisAccount, this);
    }

    @Override
    public void onSuccess(List<TypePersonBean> mFollow) {
        mView.loadFollow(mFollow);
        mView.hideProgress();
    }

    @Override
    public void onSuccess(){
        mView.hideProgress();
        mView.followSuccess();
    }


    @Override
    public void onFailure(String msg, Exception e) {
        mView.onError(msg);
        mView.hideProgress();
    }
}
