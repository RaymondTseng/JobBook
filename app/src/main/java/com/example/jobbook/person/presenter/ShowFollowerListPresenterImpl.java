package com.example.jobbook.person.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.TypePersonBean;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;
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
                .subscribe(new BaseObserver<List<TypePersonBean>>() {
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
                .subscribe(new BaseObserver<String>() {
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
