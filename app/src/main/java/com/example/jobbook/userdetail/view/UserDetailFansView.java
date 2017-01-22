package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public interface UserDetailFansView {
    void loadFans(List<TypePersonBean> mFans);

    void showProgress();

    void hideProgress();

    void followSuccess();

    void onError(String msg);
}
