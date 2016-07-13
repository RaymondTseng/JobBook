package com.example.jobbook.login.view;

/**
 * Created by Xu on 2016/7/7.
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUserError();

    void setPwdError();

    void switch2Person();

    void switch2Register();

//    void changeViewPager();
}
