package com.example.jobbook.update.presenter;

import android.content.Context;

import com.example.jobbook.update.model.UpdateModel;
import com.example.jobbook.update.model.UpdateModelImpl;
import com.example.jobbook.update.view.UpdatePhoneView;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhonePresenterImpl implements UpdatePhonePresenter, UpdateModelImpl.OnUpdatePhoneListener {
    private UpdatePhoneView mView;
    private UpdateModel mModel;

    public UpdatePhonePresenterImpl(UpdatePhoneView view){
        this.mView = view;
        mModel = new UpdateModelImpl();
    }

    @Override
    public void complete(Context mContext, String account, String tel, String code) {
        mView.showProgress();
        mModel.updatePhone(mContext, account, tel, code, this);
    }

    @Override
    public void onUpdatePhoneSuccess() {
        mView.hideProgress();
        mView.success();
        mView.close();
    }

    @Override
    public void onUpdatePhoneFailure() {
        mView.hideProgress();
        mView.networkError();
    }

    @Override
    public void onCodeError() {
        mView.hideProgress();
        mView.codeError();
    }

    @Override
    public void onCodeBlankError() {
        mView.hideProgress();
        mView.codeBlankError();
    }

    @Override
    public void onNewPhoneBlankError() {
        mView.hideProgress();
        mView.newPhoneBlankError();
    }
}
