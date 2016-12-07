package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.userdetail.model.UserDetailFollowModel;
import com.example.jobbook.userdetail.model.UserDetailFollowModelImpl;
import com.example.jobbook.userdetail.view.UserDetailFollowView;

import java.util.List;


/**
 * Created by root on 16-11-30.
 */

public class UserDetailFollowPresenterImpl implements UserDetailFollowPresenter,
        UserDetailFollowModelImpl.OnLoadFollowListener {
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
    public void onSuccess(List<PersonBean> mFollow) {
        mView.loadFollow(mFollow);
        mView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.onError();
        mView.hideProgress();
    }
}
