package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.ShowMomentListView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<MomentBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        view.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(List<MomentBean> list) {
                        view.hideProgress();
                        view.loadFanList(list);
                    }
                });
    }

}
