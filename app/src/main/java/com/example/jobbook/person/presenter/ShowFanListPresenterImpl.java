package com.example.jobbook.person.presenter;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.person.view.ShowFanListView;

import java.util.List;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowFanListPresenterImpl implements ShowFanListPresenter {

    private ShowFanListView view;

    public ShowFanListPresenterImpl(ShowFanListView view) {
        this.view = view;
    }

    @Override
    public void loadFans(String account) {
        String myAccount = MyApplication.getAccount() != null ? MyApplication.getAccount() : "";
        RetrofitService.loadFanList(account, myAccount)
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
