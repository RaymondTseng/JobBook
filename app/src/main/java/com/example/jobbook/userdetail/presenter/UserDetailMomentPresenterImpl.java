package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.userdetail.model.UserDetailMomentModel;
import com.example.jobbook.userdetail.model.UserDetailMomentModelImpl;
import com.example.jobbook.userdetail.view.UserDetailMomentView;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailMomentPresenterImpl implements UserDetailMomentPresenter,
        UserDetailMomentModelImpl.OnLoadUserDetailMomentListener {
    private UserDetailMomentView mView;
    private UserDetailMomentModel mModel;

    public UserDetailMomentPresenterImpl(UserDetailMomentView mView){
        this.mView = mView;
        this.mModel = new UserDetailMomentModelImpl();
    }
    @Override
    public void loadMoments(String account) {
        mView.showProgress();
        mModel.loadUserDetailMoments(account, this);
    }

    @Override
    public void onSuccess(List<MomentBean> list) {
        mView.hideProgress();
        mView.loadMoments(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();

    }
}
