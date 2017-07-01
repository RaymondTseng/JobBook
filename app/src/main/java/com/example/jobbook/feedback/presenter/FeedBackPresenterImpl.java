package com.example.jobbook.feedback.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.FeedBackBean;
import com.example.jobbook.feedback.view.FeedBackView;
import com.example.jobbook.network.RetrofitService;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackPresenterImpl implements FeedBackPresenter {

//    private FeedBackModel mFeedBackModel;
    private FeedBackView mFeedBackView;

    public FeedBackPresenterImpl(FeedBackView view) {
        mFeedBackView = view;
//        mFeedBackModel = new FeedBackModelImpl();
    }

    @Override
    public void feedback(String mailAddress, String content) {
//        mFeedBackView.showProgress();
//        mFeedBackModel.feedBack(mailAddress, content, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        FeedBackBean feedBackBean = new FeedBackBean();
        feedBackBean.setEmail(mailAddress);
        feedBackBean.setContent(content);
        RetrofitService.feedback(account, feedBackBean)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mFeedBackView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.getMessage(), throwable);
                        mFeedBackView.hideProgress();
                        mFeedBackView.showError();
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mFeedBackView.hideProgress();
                            mFeedBackView.showSuccess();
                            mFeedBackView.back();
                        } else {
                            if (resultBean.getResponse().equals("email failed")) {
                                mFeedBackView.hideProgress();
                                mFeedBackView.emailError();
                            }
                        }
                    }
                });
    }

}
