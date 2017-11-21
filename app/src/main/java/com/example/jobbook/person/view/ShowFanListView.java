package com.example.jobbook.person.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowFanListView extends IBaseView {

    void loadFanList(List<TypePersonBean> list);

    void followSuccess();
}
