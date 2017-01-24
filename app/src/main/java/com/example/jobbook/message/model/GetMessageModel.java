package com.example.jobbook.message.model;

/**
 * Created by Xu on 2016/12/9.
 */

public interface GetMessageModel {

    void getMessages(String account, GetMessageModelImpl.OnGetMessageListener listener);
}
