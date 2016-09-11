package com.example.jobbook.register.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.register.model.RegisterModel;
import com.example.jobbook.register.model.RegisterModelImpl;
import com.example.jobbook.register.view.RegisterView;


public class RegisterPresenterImpl implements RegisterPresenter, RegisterModelImpl.OnRegisterFinishedListener {

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView view) {
        mRegisterView = view;
        mRegisterModel = new RegisterModelImpl();
    }

    @Override
    public void registerCheck(Context mContext, String account, String userName, String password,
                              String passwordConfirm, String code) {
        mRegisterView.showProgress();
        mRegisterModel.register(mContext, account, userName, password, passwordConfirm, code, this);
    }

    @Override
    public void destroy() {
        mRegisterView = null;
    }


    @Override
    public void onAccountIllegalError() {
        mRegisterView.hideProgress();
        mRegisterView.accountIllegalError();
    }

    @Override
    public void onAccountBlankError() {
        mRegisterView.hideProgress();
        mRegisterView.accountBlankError();
    }

    @Override
    public void onUserNameBlankError() {
        mRegisterView.hideProgress();
        mRegisterView.userNameBlankError();
    }

    @Override
    public void onPwdBlankError() {
        mRegisterView.hideProgress();
        mRegisterView.pwdBlankError();
    }

    @Override
    public void onPwdConfirmBlankError() {
        mRegisterView.hideProgress();
        mRegisterView.pwdConfirmBlankError();
    }

    @Override
    public void onPwdNotEqualError() {
        mRegisterView.hideProgress();
        mRegisterView.pwdNotEqualError();
    }

    @Override
    public void onCodeBlankError() {
        mRegisterView.hideProgress();
        mRegisterView.codeBlankError();
    }

    @Override
    public void onCodeError() {
        mRegisterView.hideProgress();
        mRegisterView.codeError();
    }

    @Override
    public void onAccountExistError() {
        mRegisterView.hideProgress();
        mRegisterView.accountExistError();
    }

    @Override
    public void onSuccess(PersonBean personBean) {
        mRegisterView.hideProgress();
        mRegisterView.success();
        mRegisterView.switch2Person(personBean);
    }

    @Override
    public void onNetworkError() {
        mRegisterView.hideProgress();
        mRegisterView.networkError();
    }


}