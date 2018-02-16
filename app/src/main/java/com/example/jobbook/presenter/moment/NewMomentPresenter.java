package com.example.jobbook.presenter.moment;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.moment.NewMomentContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by Xu on 2018/2/16.
 */

public class NewMomentPresenter extends RxPresenter<NewMomentContract.View> implements NewMomentContract.Presenter {

    public NewMomentPresenter(NewMomentContract.View view) {
        attach(view);
    }

    @Override
    public void newmoment(MomentBean momentBean) {
        RetrofitService.newMoment(momentBean)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showSuccess();
                        mView.close();
                    }
                });
    }
}
