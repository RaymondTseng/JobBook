package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailFollowView {

    void loadFollow(List<TypePersonBean> mFollow);

    void showProgress();

    void hideProgress();

    void onError(String msg);

    void followSuccess();
}
