package com.example.jobbook.update.view;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdateUsernameView {

    void close();

    void complete();

    void usernameBlankError();

    void success();

    void networkError();

    void showProgress();

    void hideProgress();
}
