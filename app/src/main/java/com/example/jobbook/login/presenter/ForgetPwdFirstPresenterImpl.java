package com.example.jobbook.login.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.login.view.ForgetPwdFirstView;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.util.SMSSDKManager;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdFirstPresenterImpl implements ForgetPwdFirstPresenter {
    private ForgetPwdFirstView mView;

    public ForgetPwdFirstPresenterImpl(ForgetPwdFirstView mView){
        this.mView = mView;
    }

    @Override
    public void checkAccount(String phone) {
        if(TextUtils.isEmpty(phone)) {
            mView.hideProgress();
            mView.phoneBlankError();
        } else {
            RetrofitService.checkAccount(phone)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Subscriber<ResultBean<String>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.hideProgress();
                            mView.checkFailure(1);
                        }

                        @Override
                        public void onNext(ResultBean<String> resultBean) {
                            if (resultBean.getStatus().equals("true")) {
                                mView.hideProgress();
                                mView.checkSuccess();
                            } else {
                                mView.hideProgress();
                                mView.checkFailure(1);
                            }
                        }
                    });
        }

    }

    @Override
    public void next(Context mContext, String code, String phone) {
        mView.showProgress();
        if(TextUtils.isEmpty(code)){
            mView.hideProgress();
            mView.codeBlankError();
        }else{
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
