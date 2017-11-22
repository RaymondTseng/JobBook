package com.example.jobbook.login.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.login.view.ForgetPwdFirstView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.SMSSDKManager;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdFirstPresenterImpl implements ForgetPwdFirstPresenter {
    private ForgetPwdFirstView mView;

    public ForgetPwdFirstPresenterImpl(ForgetPwdFirstView mView) {
        this.mView = mView;
    }

    @Override
    public void checkAccount(String phone) {
        mView.showProgress();
        if (TextUtils.isEmpty(phone)) {
            mView.hideProgress();
            mView.phoneBlankError();
            return;
        } else {
            RetrofitService.checkAccount(phone)
                    .subscribe(new BaseObserver<String>() {
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
        mView.showProgress();
        if (TextUtils.isEmpty(code)) {
            mView.hideProgress();
            mView.codeBlankError();
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
