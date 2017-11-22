package com.example.jobbook.userdetail.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailMomentView extends IBaseView {

    void loadMoments(List<MomentBean> moments);
}
