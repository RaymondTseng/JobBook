package com.example.jobbook.register.presenter;

import android.widget.Toast;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.register.model.RegisterModel;
import com.example.jobbook.register.model.RegisterModelImpl;
import com.example.jobbook.register.view.RegisterView;


public class RegisterPresenterImpl implements RegisterPresenter, RegisterModelImpl.OnRegisterFinishedListener{

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView view) {
        mRegisterView = view;
        mRegisterModel = new RegisterModelImpl();
    }

    @Override
    public void registerCheck(String account, String password, String passwordConfirm) {
        mRegisterModel.register(account, password, passwordConfirm, this);
    }

    @Override
    public void destroy() {
        mRegisterView = null;
    }

    @Override
    public void onAccountBlankError() {
        mRegisterView.accountBlankError();
    }

    @Override
    public void onPwdBlankError() {
        mRegisterView.pwdBlankError();
    }

    @Override
    public void onPwdConfirmBlankError() {
        mRegisterView.pwdConfirmBlankError();
    }

    @Override
    public void onPwdNotEqualError() {
        mRegisterView.pwdNotEqualError();
    }

    @Override
    public void onAccountExistError() {
        mRegisterView.accountExistError();
    }

    @Override
    public void onSuccess(PersonBean personBean) {
        mRegisterView.success();
        mRegisterView.switch2Person(personBean);
    }

    @Override
    public void onNetworkError() {
        mRegisterView.networkError();
    }
}