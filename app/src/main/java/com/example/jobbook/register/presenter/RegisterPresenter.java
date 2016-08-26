package com.example.jobbook.register.presenter;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterPresenter {

    void registerCheck(String account, String password, String passwordConfirm);

    void destroy();
}
