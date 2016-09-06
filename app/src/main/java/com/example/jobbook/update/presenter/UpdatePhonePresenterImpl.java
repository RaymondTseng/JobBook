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
    public void complete(Context mContext, String tel, String code) {
        mModel.updatePhone(mContext, tel, code, this);
    }

    @Override
    public void onSuccess() {
        mView.success();
    }

    @Override
    public void onFailure() {
        mView.networkError();
    }

    @Override
    public void onCodeBlankError() {
        mView.codeBlankError();
    }

    @Override
    public void onNewPhoneBlankError() {
        mView.newPhoneBlankError();
    }
}
