package com.example.jobbook.feedback.view;

/**
 * Created by Xu on 2016/7/17.
 */
public interface FeedBackView {

    void showError();

    void emailError();

    void showSuccess();

    void back();

    void showProgress();

    void hideProgress();
}
