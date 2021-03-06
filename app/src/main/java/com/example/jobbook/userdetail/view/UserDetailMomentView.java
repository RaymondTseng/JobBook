package com.example.jobbook.userdetail.view;

import com.example.jobbook.bean.MomentBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailMomentView {

    void loadMoments(List<MomentBean> moments);

    void showProgress();

    void hideProgress();
}
