package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.TypePersonBean;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailView {

    void followSuccess();

    void onFail(String msg);

    void unfollowSuccess();

    void hideProgress();

    void showProgress();

    void loadSuccess(TypePersonBean personBean);

    void onRefreshSuccess(TypePersonBean personBean);

}
