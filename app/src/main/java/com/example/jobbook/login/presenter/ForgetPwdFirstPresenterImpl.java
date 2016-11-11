package com.example.jobbook.login.presenter;

import android.content.Context;
import android.util.Log;

import com.example.jobbook.login.model.ForgetPwdModel;
import com.example.jobbook.login.model.ForgetPwdModelImpl;
import com.example.jobbook.login.view.ForgetPwdFirstView;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdFirstPresenterImpl implements ForgetPwdFirstPresenter, ForgetPwdModelImpl.OnCheckAccountListener,
        ForgetPwdModelImpl.OnCheckCodeListener{
    private ForgetPwdFirstView mView;
    private ForgetPwdModel mModel;

    public ForgetPwdFirstPresenterImpl(ForgetPwdFirstView mView){
        this.mView = mView;
        mModel = new ForgetPwdModelImpl();
    }

    @Override
    public void checkAccount(String phone) {
        Log.i("forgetpwd", phone);
        mView.showProgress();
        mModel.checkAccount(phone, this);
    }

    @Override
    public void next(Context mContext, String code, String phone) {
        mView.showProgress();
        mModel.checkCode(mContext, code, phone, this);
    }

    @Override
    public void onCheckAccountSuccess() {
        mView.hideProgress();
        mView.checkSuccess();
    }

    @Override
    public void onCheckAccountFailure(int error) {
        mView.hideProgress();
        mView.checkFailure(error);
    }

    @Override
    public void onCheckCodeSuccess() {
        mView.hideProgress();
        mView.codeSuccess();
    }

    @Override
    public void onCheckCodeFailure() {
        mView.hideProgress();
        mView.codeFailure();
    }

    @Override
    public void onCodeBlankError() {
        mView.hideProgress();
        mView.codeBlankError();
    }

    @Override
    public void onPhoneBlankError() {
        mView.hideProgress();
        mView.phoneBlankError();
    }
}
