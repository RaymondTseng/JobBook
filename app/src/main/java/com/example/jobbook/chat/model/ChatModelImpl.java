package com.example.jobbook.chat.model;

import com.example.jobbook.bean.ChatBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/19.
 */
public class ChatModelImpl implements ChatModel {

    @Override
    public void receive(OnReceiveListener listener) {

    }

    @Override
    public void send(ChatBean chatBean, OnSendListener listener) {

    }

    public interface OnReceiveListener {
        void onReceiveFinish(List<ChatBean> chats);
        void onReceiveFailure();
    }

    public interface OnSendListener {
        void onSendFinish(List<ChatBean> chats);
        void onSendFailure();
    }
}
