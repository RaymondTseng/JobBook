package com.example.jobbook.presenter.person;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.FanListContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

public class FanListPresenter extends RxPresenter<FanListContract.View> implements FanListContract.Presenter {
    
    public FanListPresenter(FanListContract.View mView) {
        attach(mView);
    }

    @Override
    public void loadFans(String account) {
        String myAccount = MyApplication.getAccount() != null ? MyApplication.getAccount() : "";
        addSubscribe(RetrofitService.loadFanList(account, myAccount)
                .subscribeWith(new BaseSubscriber<List<TypePersonBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<TypePersonBean> typePersonBeans) {
                        mView.loadFanList(typePersonBeans);
                    }
                }));
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        addSubscribe(RetrofitService.follow(myAccount, hisAccount)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.followSuccess();
                    }
                }));
    }
}
