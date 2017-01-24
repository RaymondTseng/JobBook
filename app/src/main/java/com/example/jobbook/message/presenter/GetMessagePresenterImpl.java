package com.example.jobbook.message.presenter;

import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.message.model.GetMessageModel;
import com.example.jobbook.message.model.GetMessageModelImpl;
import com.example.jobbook.message.view.GetMessageView;

import java.util.List;

/**
 * Created by Xu on 2016/12/9.
 */

public class GetMessagePresenterImpl implements GetMessagePresenter, GetMessageModelImpl.OnGetMessageListener {

    private GetMessageModel model;
    private GetMessageView view;

    public GetMessagePresenterImpl(GetMessageView view) {
        model = new GetMessageModelImpl();
        this.view = view;
    }

    @Override
    public void getMessage(String account) {
        view.showProgress();
        model.getMessages(account, this);
    }

    @Override
    public void onSuccess(List<MessageBean> lists) {
        view.hideProgress();
        view.getMessage(lists);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.showLoadFailMsg();
        view.hideProgress();
    }
}
