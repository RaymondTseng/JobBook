package com.example.jobbook.moment.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.moment.view.NewMomentView;
import com.example.jobbook.network.RetrofitService;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentPresenterImpl implements NewMomentPresenter {

    private NewMomentView mNewMomentView;

    public NewMomentPresenterImpl(NewMomentView view) {
        mNewMomentView = view;
    }

    @Override
    public void newmoment(MomentBean momentBean) {
        RetrofitService.newMoment(momentBean)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mNewMomentView.showProgress();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mNewMomentView.hideProgress();
                        mNewMomentView.showError();
                    }

                    @Override
                    public void onNext(String s) {
                        mNewMomentView.hideProgress();
                        mNewMomentView.showSuccess();
                        mNewMomentView.close();
                    }
                });
    }

}
