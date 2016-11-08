package com.example.jobbook.moment.view;

/**
 * Created by Xu on 2016/7/16.
 */
public interface NewMomentView {

    void showError();

    void showSuccess();

    void publishNoLoginError();

    void close();

    void showProgress();

    void hideProgress();

}
