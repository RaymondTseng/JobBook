package com.example.jobbook.moment.model;

import com.example.jobbook.bean.MomentBean;

/**
 * Created by Xu on 2016/7/16.
 */
public interface NewMomentModel {

    void newmoment(MomentBean momentBean, NewMomentModelImpl.OnNewMomentListener listener);
}
