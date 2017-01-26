package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.PersonBean;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailView {

    void followSuccess();

    void followFail();

    void hideProgress();

    void showProgress();

    void loadSuccess(PersonBean personBean);

    void loadFail();
}
