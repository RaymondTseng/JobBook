package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.person.model.ShowFollowerListModel;
import com.example.jobbook.person.model.ShowFollowerListModelImpl;
import com.example.jobbook.person.view.ShowFollowerListView;

import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListPresenterImpl implements ShowFollowerListPresenter, ShowFollowerListModelImpl.OnLoadFollowerListListener {

    private ShowFollowerListView view;
    private ShowFollowerListModel model;

    public ShowFollowerListPresenterImpl(ShowFollowerListView view) {
        this.view = view;
        this.model = new ShowFollowerListModelImpl();
    }

    @Override
    public void loadFollwers(String account) {
        view.showProgress();
        model.loadFollowerList(account, this);
    }

    @Override
    public void onSuccess(List<PersonBean> list) {
        view.loadFanList(list);
        view.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
        view.showLoadFailMsg();
    }
}
