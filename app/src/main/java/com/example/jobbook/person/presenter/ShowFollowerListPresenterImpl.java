package com.example.jobbook.person.presenter;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.person.view.ShowFollowerListView;

import java.util.List;

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
                .subscribe(new BaseSubscriber<List<TypePersonBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return view;
                    }

                    @Override
                    public void onNext(List<TypePersonBean> typePersonBeans) {
                        view.loadFanList(typePersonBeans);
                    }
                });
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        RetrofitService.follow(myAccount, hisAccount)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return view;
                    }

                    @Override
                    public void onNext(String s) {
                        view.followSuccess();
                    }
                });
    }

}
