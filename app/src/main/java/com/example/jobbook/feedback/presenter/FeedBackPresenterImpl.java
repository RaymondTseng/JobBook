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
        mFeedBackView.showProgress();
        mFeedBackModel.feedBack(mailAddress, content, this);
    }

    @Override
    public void onSuccess() {
        mFeedBackView.hideProgress();
        mFeedBackView.showSuccess();
        mFeedBackView.back();
    }

    @Override
    public void onFailure() {
        mFeedBackView.hideProgress();
        mFeedBackView.showError();
    }

    @Override
    public void onEmailError() {
        mFeedBackView.hideProgress();
        mFeedBackView.emailError();
    }

}
