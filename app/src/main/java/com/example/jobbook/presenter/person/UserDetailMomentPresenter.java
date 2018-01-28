package com.example.jobbook.presenter.person;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UserDetailMomentContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2018/1/28.
 */

public class UserDetailMomentPresenter extends RxPresenter<UserDetailMomentContract.View> implements UserDetailMomentContract.Presenter {

    public UserDetailMomentPresenter(UserDetailMomentContract.View view) {
        attach(view);
    }

    @Override
    public void loadMoments(String hisAccount, String myAccount) {
        RetrofitService.loadMomentList(hisAccount, myAccount)
                .subscribe(new BaseSubscriber<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mView.loadMoments(momentBeans);
                    }
                });
    }
}
