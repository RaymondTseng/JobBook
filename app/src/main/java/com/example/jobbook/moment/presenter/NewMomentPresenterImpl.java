package com.example.jobbook.moment.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.moment.view.NewMomentView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentPresenterImpl implements NewMomentPresenter {

    private NewMomentView mNewMomentView;

    public NewMomentPresenterImpl(NewMomentView view) {
        mNewMomentView = view;
    }

    @Override
    public void newmoment(final MomentBean momentBean) {
        RetrofitService.newMoment(momentBean)
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mNewMomentView;
                    }

                    @Override
                    public void onNext(String s) {
                        mNewMomentView.showSuccess();
                        mNewMomentView.close();
                    }
                });
    }

}
