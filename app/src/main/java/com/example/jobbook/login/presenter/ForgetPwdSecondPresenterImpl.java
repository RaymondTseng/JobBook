package com.example.jobbook.login.presenter;

import com.example.jobbook.login.model.ForgetPwdModel;
import com.example.jobbook.login.model.ForgetPwdModelImpl;
import com.example.jobbook.login.view.ForgetPwdSecondView;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdSecondPresenterImpl implements ForgetPwdSecondPresenter, ForgetPwdModelImpl.OnCompleteListener {
    private ForgetPwdSecondView mView;
    private ForgetPwdModel mModel;

    public ForgetPwdSecondPresenterImpl(ForgetPwdSecondView mView){
        this.mView = mView;
        mModel = new ForgetPwdModelImpl();
    }
    @Override
    public void complete(String account, String password, String confirm) {
        mView.showProgress();
        mModel.complete(account, password, confirm, this);
    }

    @Override
    public void onCompleteSuccess() {
        mView.hideProgress();
        mView.success();
    }

    @Override
    public void onCompleteFailure() {
        mView.hideProgress();
        mView.failure();
    }

    @Override
    public void onPasswordBlankError() {
        mView.hideProgress();
        mView.phoneBlankError();
    }

    @Override
    public void onConfirmBlankError() {
        mView.hideProgress();
        mView.confirmPhoneBlankError();
    }

    @Override
    public void onDifferentError() {
        mView.hideProgress();
        mView.differentError();
    }
}
