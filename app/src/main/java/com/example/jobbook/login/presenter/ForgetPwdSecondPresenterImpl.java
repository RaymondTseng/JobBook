package com.example.jobbook.login.presenter;

import android.text.TextUtils;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.login.view.ForgetPwdSecondView;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdSecondPresenterImpl implements ForgetPwdSecondPresenter {
    private ForgetPwdSecondView mView;

    public ForgetPwdSecondPresenterImpl(ForgetPwdSecondView mView) {
        this.mView = mView;
    }

    @Override
    public void complete(String account, String password, String confirm) {
        mView.showProgress();
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
            RetrofitService.changePwdComplete(account, password)
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(String s) {
                            mView.success();
                        }
                    });
        }
    }

}
