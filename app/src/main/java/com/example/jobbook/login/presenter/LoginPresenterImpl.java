package com.example.jobbook.login.presenter;

import com.example.jobbook.login.model.LoginModel;
import com.example.jobbook.login.model.LoginModelImpl;
import com.example.jobbook.login.view.LoginView;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginModelImpl.OnLoginFinishedListener {

    private LoginModel mLoginModel;
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
        mLoginModel = new LoginModelImpl();
    }

    @Override
    public void loginCheck(String username, String password) {
        mLoginModel.login(username, password, this);
    }

    @Override
    public void destroy() {
        mLoginView = null;
    }

    @Override
    public void onUsernameError() {
        mLoginView.setUserError();
    }

    @Override
    public void onPasswordError() {
        mLoginView.setPwdError();
    }

    @Override
    public void onSuccess() {
        mLoginView.switch2Person();
    }

    @Override
    public void onNetWorkError() {
        mLoginView.setNetworkError();
    }
}
