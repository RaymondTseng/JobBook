package com.example.jobbook.person.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.person.view.ShowMomentListView;

import java.util.List;

/**
 * Created by Xu on 2016/12/8.
 */

public class ShowMomentListPresenterImpl implements ShowMomentListPresenter {

    private ShowMomentListView view;

    public ShowMomentListPresenterImpl(ShowMomentListView view) {
        this.view = view;
    }

    @Override
    public void loadMomentList(String hisAccount, String myAccount) {
        RetrofitService.loadMomentList(hisAccount, myAccount)
                .subscribe(new BaseObserver<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return view;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        view.loadFanList(momentBeans);
                    }
                });
    }

}
