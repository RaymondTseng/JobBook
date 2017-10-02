package com.example.jobbook.person.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.ShowFollowerListView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFollowerListPresenterImpl implements ShowFollowerListPresenter {

    private ShowFollowerListView view;

    public ShowFollowerListPresenterImpl(ShowFollowerListView view) {
        this.view = view;
    }

    @Override
    public void loadFollwers(String account) {
        String myAccount = MyApplication.getAccount() == null ? "" : MyApplication.getAccount();
        RetrofitService.loadFollowerList(account, myAccount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<TypePersonBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        view.showLoadFailMsg(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TypePersonBean> list) {
                        view.hideProgress();
                        view.loadFanList(list);
                    }
                });
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        RetrofitService.follow(myAccount, hisAccount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showProgress();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgress();
                        view.showLoadFailMsg(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        view.hideProgress();
                        view.followSuccess();
                    }
                });
    }

}
