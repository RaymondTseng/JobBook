package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.person.model.ShowMomentListModel;
import com.example.jobbook.person.model.ShowMomentListModelImpl;
import com.example.jobbook.person.view.ShowMomentListView;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public class ShowMomentListPresenterImpl implements ShowMomentListPresenter, ShowMomentListModelImpl.OnLoadMomentListListener {

    private ShowMomentListView view;
    private ShowMomentListModel model;

    public ShowMomentListPresenterImpl(ShowMomentListView view) {
        this.view = view;
        this.model = new ShowMomentListModelImpl();
    }

    @Override
    public void loadMomentList(String account) {
        view.showProgress();
        model.loadMomentList(account, this);
    }

    @Override
    public void onSuccess(List<MomentBean> list) {
        view.loadFanList(list);
        view.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
        view.showLoadFailMsg();
    }
}
