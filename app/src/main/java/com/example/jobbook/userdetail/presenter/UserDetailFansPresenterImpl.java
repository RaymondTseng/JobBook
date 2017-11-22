package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailFansView;

import java.util.List;

/**
 * Created by root on 16-11-30.
 */

public class UserDetailFansPresenterImpl implements UserDetailFansPresenter {
    private UserDetailFansView mView;

    public UserDetailFansPresenterImpl(UserDetailFansView mView){
        this.mView = mView;
    }
    @Override
    public void loadFans(String account) {
        RetrofitService.loadFanList(account, "")
                .subscribe(new BaseObserver<List<TypePersonBean>>() {
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
                .subscribe(new BaseObserver<String>() {
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
