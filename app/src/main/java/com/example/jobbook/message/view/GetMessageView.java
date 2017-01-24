package com.example.jobbook.message.view;

import com.example.jobbook.bean.MessageBean;

import java.util.List;

/**
 * Created by Xu on 2016/12/9.
 */

public interface GetMessageView {

    void getMessage(List<MessageBean> list);

    void showProgress();

    void hideProgress();

    void showLoadFailMsg();
}
