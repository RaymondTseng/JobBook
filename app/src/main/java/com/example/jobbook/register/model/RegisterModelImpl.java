package com.example.jobbook.register.model;

/**
 * Created by Xu on 2016/7/7.
 */
public class RegisterModelImpl implements RegisterModel{

    @Override
    public void register(String username, String password) {

    }

    public interface OnRegisterFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }
}
