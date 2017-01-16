package com.example.jobbook.person.presenter;

import com.example.jobbook.person.model.ShowMomentListModel;
import com.example.jobbook.person.model.ShowMomentListModelImpl;
import com.example.jobbook.person.view.ShowMomentListView;

/**
 * Created by Xu on 2016/12/8.
 */

public class ShowMomentListPresenterImpl implements ShowMomentListPresenter {

    private ShowMomentListView view;
    private ShowMomentListModel model;

    public ShowMomentListPresenterImpl(ShowMomentListView view) {
        this.view = view;
        this.model = new ShowMomentListModelImpl();
    }
}
