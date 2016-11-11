package com.example.jobbook.main.view;

import android.content.Context;

import com.example.jobbook.bean.PersonBean;

/**
 * Created by Xu on 2016/7/5.
 */
public interface MainView {

    void switch2Job();

    void switch2Article();

    void switch2Question();

    void switch2Container();

    void loginCheckSuccess(PersonBean personBean);

    void loginCheckTimeOut();

}
