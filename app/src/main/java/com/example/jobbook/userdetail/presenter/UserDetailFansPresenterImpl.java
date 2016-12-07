package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.userdetail.model.UserDetailFansModel;
import com.example.jobbook.userdetail.model.UserDetailFansModelImpl;
import com.example.jobbook.userdetail.view.UserDetailFansView;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansPresenterImpl implements UserDetailFansPresenter,
        UserDetailFansModelImpl.OnLoadFansListener {
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
    public void onSuccess(List<PersonBean> mFans) {
        mView.hideProgress();
        mView.loadFans(mFans);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.onError();
        mView.hideProgress();
    }
}
