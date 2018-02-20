package com.example.jobbook.presenter.account;

import android.text.TextUtils;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.account.ForgetPwdContract;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by zhaoxuzhang on 2018/1/27.
 */

public class ForgetPwdSecondPresenter extends RxPresenter<ForgetPwdContract.ForgetPwdSecondView> implements ForgetPwdContract.ForgetPwdSecondPresenter {

    public ForgetPwdSecondPresenter(ForgetPwdContract.ForgetPwdSecondView view) {
        attach(view);
    }

    @Override
    public void complete(String account, String password, String confirm) {
        if (TextUtils.isEmpty(account)) {
            return;
        } else if (TextUtils.isEmpty(password)) {
            mView.hideProgress();
            mView.phoneBlankError();
            return;
        } else if (TextUtils.isEmpty(confirm)) {
            mView.hideProgress();
            mView.confirmPhoneBlankError();
            return;
        } else if (!TextUtils.equals(password, confirm)) {
            mView.hideProgress();
            mView.differentError();
            return;
        } else {
            addSubscribe(RetrofitService.changePwdComplete(account, password)
                    .subscribeWith((new BaseSubscriber<String>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(String s) {
                            mView.success();
                        }
                    })));
        }
    }
}
