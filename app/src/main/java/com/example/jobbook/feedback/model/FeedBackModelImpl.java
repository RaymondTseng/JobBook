package com.example.jobbook.feedback.model;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackModelImpl implements FeedBackModel {

    @Override
    public void feedBack(String mailAddress, String content, OnFeedBackListener listener) {

    }

    public interface OnFeedBackListener {
        void onSuccess();
        void onFailure();
    }
}
