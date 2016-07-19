package com.example.jobbook.chat.view;

import com.example.jobbook.bean.ChatBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/19.
 */
public interface ChatView {

    void showProgress();

    void hideProgress();

    void setChatItems(List<ChatBean> chatItems);

}
