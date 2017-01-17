package com.example.jobbook.person.view;

import com.example.jobbook.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowMomentListView {

    void showProgress();

    void hideProgress();

    void showLoadFailMsg();

    void loadFanList(List<MomentBean> list);
}
