package com.example.jobbook.userdetail.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.TypePersonBean;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public interface UserDetailFollowView extends IBaseView {

    void loadFollow(List<TypePersonBean> mFollow);

    void onError(String msg);

    void followSuccess();
}
