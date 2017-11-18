package com.example.jobbook.message.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.message.view.GetMessageView;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

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
                .subscribe(new BaseObserver<List<MessageBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return view;
                    }

                    @Override
                    public void onNext(List<MessageBean> messageBeans) {
                        view.getMessage(messageBeans);
                    }
                });
    }

}
