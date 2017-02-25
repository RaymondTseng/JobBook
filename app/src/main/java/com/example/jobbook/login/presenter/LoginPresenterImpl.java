package com.example.jobbook.login.presenter;

import com.example.jobbook.bean.PersonBean;
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
    public void loginCheck(String account, String password) {
        mLoginView.showProgress();
        mLoginModel.login(account, password, this);
    }

    @Override
    public void destroy() {
        mLoginView = null;
    }

//    @Override
//    public void onUsernameError() {
//        mLoginView.setUserError();
//    }


    @Override
    public void onUserError() {
        mLoginView.hideProgress();
        mLoginView.setUserError();
    }

    @Override
    public void onAccountError() {
        mLoginView.hideProgress();
        mLoginView.setAccountError();
    }

    @Override
    public void onPasswordError() {
        mLoginView.hideProgress();
        mLoginView.setPasswordError();
    }

    @Override
    public void onSuccess(PersonBean personBean) {
        mLoginView.hideProgress();
        mLoginView.switch2Person(personBean);
    }

    @Override
    public void onNetWorkError() {
        mLoginView.hideProgress();
        mLoginView.setNetworkError();
    }
}