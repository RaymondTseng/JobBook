package com.example.jobbook.chat.presenter;

import com.example.jobbook.bean.ChatBean;

/**
 * Created by Xu on 2016/7/19.
 */
public interface ChatPresenter {

    void send(ChatBean chatBean);

    void receive();
}
