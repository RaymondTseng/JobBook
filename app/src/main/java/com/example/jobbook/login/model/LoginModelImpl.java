package com.example.jobbook.login.model;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

    }

    public interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }
}
