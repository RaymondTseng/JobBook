package com.example.jobbook.userdetail.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.userdetail.view.UserDetailMomentView;

import java.util.List;

/**
 * Created by root on 16-11-28.
 */

public class UserDetailMomentPresenterImpl implements UserDetailMomentPresenter {
    private UserDetailMomentView mView;

    public UserDetailMomentPresenterImpl(UserDetailMomentView mView) {
        this.mView = mView;
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
