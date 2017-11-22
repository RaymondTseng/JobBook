package com.example.jobbook.userdetail.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailView extends IBaseView {

    void followSuccess();

    void onFail(String msg);

    void unfollowSuccess();

    void loadSuccess(TypePersonBean personBean);

    void onRefreshSuccess(TypePersonBean personBean);

}
