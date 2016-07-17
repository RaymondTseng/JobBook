package com.example.jobbook.feedback.presenter;

import com.example.jobbook.feedback.model.FeedBackModel;
import com.example.jobbook.feedback.model.FeedBackModelImpl;
import com.example.jobbook.feedback.view.FeedBackView;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackPresenterImpl implements FeedBackPresenter, FeedBackModelImpl.OnFeedBackListener {

    private FeedBackModel mFeedBackModel;
    private FeedBackView mFeedBackView;

    public FeedBackPresenterImpl(FeedBackView view) {
        mFeedBackView = view;
        mFeedBackModel = new FeedBackModelImpl();
    }

    @Override
    public void feedback(String mailAddress, String content) {
        mFeedBackModel.feedBack(mailAddress, content, this);
    }

    @Override
    public void onSuccess() {
        mFeedBackView.showSuccess();
        mFeedBackView.switch2Main();
    }

    @Override
    public void onFailure() {
        mFeedBackView.showError();
        mFeedBackView.back();
    }


}
