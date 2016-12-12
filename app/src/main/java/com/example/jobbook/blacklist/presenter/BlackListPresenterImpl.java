package com.example.jobbook.blacklist.presenter;

import com.example.jobbook.blacklist.model.BlackListModel;
import com.example.jobbook.blacklist.model.BlackListModelImpl;
import com.example.jobbook.blacklist.view.BlackListView;

/**
 * Created by Xu on 2016/12/8.
 */

public class BlackListPresenterImpl implements BlackListPresenter {

    private BlackListView view;
    private BlackListModel model;

    public BlackListPresenterImpl(BlackListView view) {
        this.view = view;
        this.model = new BlackListModelImpl();
    }
}
