package com.example.jobbook.message.presenter;

import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.message.view.GetMessageView;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/12/9.
 */

public class GetMessagePresenterImpl implements GetMessagePresenter {

    private GetMessageView view;

    public GetMessagePresenterImpl(GetMessageView view) {
        this.view = view;
    }

    @Override
    public void getMessage(String account) {
        RetrofitService.getMessages(account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MessageBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showLoadFailMsg();
                        view.hideProgress();
                    }

                    @Override
                    public void onNext(List<MessageBean> messageBeen) {
                        view.hideProgress();
                        view.getMessage(messageBeen);
                    }
                });
    }

}
