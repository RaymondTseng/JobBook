package com.example.jobbook.chat.model;

import com.example.jobbook.bean.ChatBean;

/**
 * Created by Xu on 2016/7/17.
 */
public interface ChatModel {

    void receive(ChatModelImpl.OnReceiveListener listener);

    void send(ChatBean chatBean, ChatModelImpl.OnSendListener listener);
}