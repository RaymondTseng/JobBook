package com.example.jobbook.feedback.model;

/**
 * Created by Xu on 2016/7/17.
 */
public interface FeedBackModel {
    void feedBack(String mailAddress, String content, FeedBackModelImpl.OnFeedBackListener listener);
}
