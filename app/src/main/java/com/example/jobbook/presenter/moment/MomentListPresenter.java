package com.example.jobbook.presenter.moment;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.moment.MomentListContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by Xu on 2018/1/28.
 */

public class MomentListPresenter extends RxPresenter<MomentListContract.View> implements MomentListContract.Presenter {

    public MomentListPresenter(MomentListContract.View view) {
        attach(view);
    }

    @Override
    public void loadMomentList(String hisAccount, String myAccount) {
        addSubscribe(RetrofitService.loadMomentList(hisAccount, myAccount)
                .subscribeWith(new BaseSubscriber<List<MomentBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<MomentBean> momentBeans) {
                        mView.loadFanList(momentBeans);
                    }
                }));
    }
}
