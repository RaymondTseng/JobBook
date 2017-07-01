package com.example.jobbook.feedback.view;

import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2016/7/17.
 */
public interface FeedBackView extends IBaseView {

    void showError();

    void emailError();

    void showSuccess();

    void back();

}
