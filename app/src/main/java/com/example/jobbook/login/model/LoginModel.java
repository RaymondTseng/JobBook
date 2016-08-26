package com.example.jobbook.login.model;

/**
 * Created by Xu on 2016/7/7.
 */
public interface LoginModel {

    void login(String account, String password, LoginModelImpl.OnLoginFinishedListener listener);
}
