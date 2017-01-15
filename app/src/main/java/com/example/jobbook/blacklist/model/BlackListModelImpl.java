package com.example.jobbook.blacklist.model;

import com.example.jobbook.bean.PersonBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public class BlackListModelImpl implements BlackListModel {

    @Override
    public void loadBlackList(OnLoadBlackListListener listener) {
//        OkHttpUtils.
    }

    public interface OnLoadBlackListListener {
        void onSuccess(List<PersonBean> list);

        void onFailure(String msg, Exception e);
    }
}
