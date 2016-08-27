package com.example.jobbook.login.view;

import com.example.jobbook.bean.PersonBean;

/**
 * Created by Xu on 2016/7/7.
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setNetworkError();

    void setUserError();

    void setAccountError();

    void setPasswordError();

    void switch2Person(PersonBean personBean);

    void switch2Register();


//    void changeViewPager();
}
