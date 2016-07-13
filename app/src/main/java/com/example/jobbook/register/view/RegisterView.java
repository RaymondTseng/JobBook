package com.example.jobbook.register.view;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterView {

    void showProgress();

    void hideProgress();

    void setUserError();

    void setPwdError();

    void switch2Person();

    void switch2Login();

//    void changeViewPager();
}
