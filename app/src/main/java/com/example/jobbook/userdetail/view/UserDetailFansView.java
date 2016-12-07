package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public interface UserDetailFansView {
    void loadFans(List<PersonBean> mFans);

    void showProgress();

    void hideProgress();

    void onError();
}
