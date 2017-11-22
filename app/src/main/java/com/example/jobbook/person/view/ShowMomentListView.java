package com.example.jobbook.person.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public interface ShowMomentListView extends IBaseView {

    void loadFanList(List<MomentBean> list);
}
