package com.example.jobbook.person.presenter;

import com.example.jobbook.person.model.ShowFollowerListModel;
import com.example.jobbook.person.model.ShowFollowerListModelImpl;
import com.example.jobbook.person.view.ShowFollowerListView;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListPresenterImpl implements ShowFollowerListPresenter {

    private ShowFollowerListView view;
    private ShowFollowerListModel model;

    public ShowFollowerListPresenterImpl(ShowFollowerListView view) {
        this.view = view;
        this.model = new ShowFollowerListModelImpl();
    }
}
