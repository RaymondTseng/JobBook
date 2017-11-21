package com.example.jobbook.update.view;

import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdateUsernameView extends IBaseView{

    void close();

    void complete();

    void usernameBlankError();

    void success();

    void networkError();
}
