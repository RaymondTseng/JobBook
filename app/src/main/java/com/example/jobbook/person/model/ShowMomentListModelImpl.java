package com.example.jobbook.person.model;

import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public class ShowMomentListModelImpl implements ShowMomentListModel {

    @Override
    public void loadMomentList(OnLoadMomentListListener listener) {
//        OkHttpUtils.
    }

    public interface OnLoadMomentListListener {
        void onSuccess(List<PersonBean> list);

        void onFailure(String msg, Exception e);
    }
}
