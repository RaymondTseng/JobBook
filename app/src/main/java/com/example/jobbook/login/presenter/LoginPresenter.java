package com.example.jobbook.login.presenter;

/**
 * Created by Xu on 2016/7/7.
 */
public interface LoginPresenter {

    void loginCheck(String account, String password);

    void destroy();
}
