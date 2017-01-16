package com.example.jobbook.person.model;

import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public class ShowFollowerListModelImpl implements ShowFollowerListModel {

    @Override
    public void loadFollowerList(OnLoadFollowerListListener listener) {
//        OkHttpUtils.
    }

    public interface OnLoadFollowerListListener {
        void onSuccess(List<PersonBean> list);

        void onFailure(String msg, Exception e);
    }
}
