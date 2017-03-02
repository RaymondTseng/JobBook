package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.person.model.ShowFanListModel;
import com.example.jobbook.person.model.ShowFantListModelImpl;
import com.example.jobbook.person.view.ShowFanListView;

import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFanListPresenterImpl implements ShowFanListPresenter, ShowFantListModelImpl.OnLoadFanListListener,
    ShowFantListModelImpl.OnFollowListener{

    private ShowFanListView view;
    private ShowFanListModel model;

    public ShowFanListPresenterImpl(ShowFanListView view) {
        this.view = view;
        model = new ShowFantListModelImpl();
    }

    @Override
    public void loadFans(String account) {
        view.showProgress();
        model.loadFanList(account, this);
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        view.showProgress();
        model.follow(myAccount, hisAccount, this);
    }

    @Override
    public void onSuccess(List<TypePersonBean> list) {
        view.loadFanList(list);
        view.hideProgress();
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.followSuccess();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
        view.showLoadFailMsg(msg);
    }
}
