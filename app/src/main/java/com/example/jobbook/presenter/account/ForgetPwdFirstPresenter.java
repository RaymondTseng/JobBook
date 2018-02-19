package com.example.jobbook.presenter.account;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.account.ForgetPwdContract;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.SMSSDKManager;

/**
 * Created by zhaoxuzhang on 2018/1/27.
 */

public class ForgetPwdFirstPresenter extends RxPresenter<ForgetPwdContract.ForgetPwdFirstView> implements ForgetPwdContract.ForgetPwdFirstPresenter{

    public ForgetPwdFirstPresenter(ForgetPwdContract.ForgetPwdFirstView view) {
        attach(view);
    }

    @Override
    public void checkAccount(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mView.hideProgress();
            mView.phoneBlankError();
            return;
        } else {
            RetrofitService.checkAccount(phone)
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(String s) {
                            mView.checkSuccess();
                        }
                    });
        }
    }

    @Override
    public void next(Context mContext, String code, String phone) {
        if (TextUtils.isEmpty(code)) {
            mView.hideProgress();
            mView.codeBlankError();
            return;
        } else {
            SMSSDKManager.getInstance().verifyCode(mContext, "86", phone, code, new SMSSDKManager.Callback() {
                @Override
                public void success() {
                    mView.hideProgress();
                    mView.codeSuccess();
                }

                @Override
                public void error(Throwable error) {
                    mView.hideProgress();
                    mView.codeFailure();
                }
            });
        }
    }
}
