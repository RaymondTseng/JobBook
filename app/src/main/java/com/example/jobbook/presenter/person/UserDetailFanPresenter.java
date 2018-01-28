package com.example.jobbook.presenter.person;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UserDetailFanContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2018/1/28.
 */

public class UserDetailFanPresenter extends RxPresenter<UserDetailFanContract.View> implements UserDetailFanContract.Presenter {

    public UserDetailFanPresenter(UserDetailFanContract.View view) {
        attach(view);
    }

    @Override
    public void loadFans(String account) {
        RetrofitService.loadFanList(account, "")
                .subscribe(new BaseSubscriber<List<TypePersonBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<TypePersonBean> typePersonBeans) {
                        mView.loadFans(typePersonBeans);
                    }
                });
    }

    @Override
    public void follow(String myAccount, String hisAccount) {
        RetrofitService.follow(myAccount, hisAccount)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.followSuccess();
                    }
                });
    }
}
