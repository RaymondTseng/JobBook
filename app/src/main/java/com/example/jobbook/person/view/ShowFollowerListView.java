package com.example.jobbook.person.view;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFollowerListView {

    void showProgress();

    void hideProgress();

    void showLoadFailMsg(String msg);

    void loadFanList(List<TypePersonBean> list);

    void followSuccess();
}
