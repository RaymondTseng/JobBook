package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailFollowView {

    void loadFollow(List<PersonBean> mFollow);

    void showProgress();

    void hideProgress();

    void onError();
}
