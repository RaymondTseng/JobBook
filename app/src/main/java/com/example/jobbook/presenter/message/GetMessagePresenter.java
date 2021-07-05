package com.example.jobbook.presenter.message;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.message.GetMessageContract;
import com.example.jobbook.model.bean.MessageBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

public class GetMessagePresenter extends RxPresenter<GetMessageContract.View> implements GetMessageContract.Presenter {

    public GetMessagePresenter(GetMessageContract.View view) {
        attach(view);
    }

    @Override
    public void getMessage(String account) {
        addSubscribe(RetrofitService.getMessages(account)
                .subscribeWith(new BaseSubscriber<List<MessageBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MessageBean> messageBeans) {
                        mView.getMessage(messageBeans);
                    }
                }));
    }
}
