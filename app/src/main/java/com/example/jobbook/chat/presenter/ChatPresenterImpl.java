package com.example.jobbook.chat.presenter;

import com.example.jobbook.bean.ChatBean;
import com.example.jobbook.chat.model.ChatModel;
import com.example.jobbook.chat.model.ChatModelImpl;
import com.example.jobbook.chat.view.ChatView;

import java.util.List;

/**
 * Created by Xu on 2016/7/19.
 */
public class ChatPresenterImpl implements ChatPresenter, ChatModelImpl.OnReceiveListener, ChatModelImpl.OnSendListener{

    private ChatModel mChatModel;
    private ChatView mChatView;

    public ChatPresenterImpl(ChatView chatView) {
        mChatModel = new ChatModelImpl();
        mChatView = chatView;
    }

    @Override
    public void send(ChatBean chatBean) {
        mChatModel.send(chatBean, this);
    }

    @Override
    public void receive() {
        mChatModel.receive(this);
    }

    @Override
    public void onReceiveFinish(List<ChatBean> chats) {
        mChatView.setChatItems(chats);
        mChatView.showProgress();
    }

    @Override
    public void onReceiveFailure() {

    }

    @Override
    public void onSendFinish(List<ChatBean> chats) {
        mChatView.setChatItems(chats);
        mChatView.showProgress();
    }

    @Override
    public void onSendFailure() {

    }
}
