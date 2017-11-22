package com.example.jobbook.userdetail.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public interface UserDetailFansView extends IBaseView {
    void loadFans(List<TypePersonBean> mFans);

    void followSuccess();

    void onError(String msg);
}
