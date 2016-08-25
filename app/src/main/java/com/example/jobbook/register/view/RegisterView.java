package com.example.jobbook.register.view;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterView {

//    void success();

    void hideProgress();

    void setNetworkError();

    void setUserNameError();

    void setPwdError();

    void switch2Person();

    void switch2Login();

//    void changeViewPager();
}
