package com.example.jobbook.register.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.register.model.RegisterModel;
import com.example.jobbook.register.model.RegisterModelImpl;
import com.example.jobbook.register.view.RegisterView;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;

import rx.Subscriber;
import rx.functions.Action0;


public class RegisterPresenterImpl implements RegisterPresenter, RegisterModelImpl.OnRegisterFinishedListener {

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView view) {
        mRegisterView = view;
        mRegisterModel = new RegisterModelImpl();
    }

    @Override
    public void registerCheck(Context mContext, String account, String userName, String password,
                              String passwordConfirm, String code) {
//
//        mRegisterModel.register(mContext, account, userName, password, passwordConfirm, code, this);
        final PersonWithDeviceTokenBean bean = new PersonWithDeviceTokenBean();
        if (TextUtils.isEmpty(account)) {
            mRegisterView.hideProgress();
            mRegisterView.accountBlankError();
            return;
        } else if (TextUtils.isEmpty(password)) {
            mRegisterView.hideProgress();
            mRegisterView.pwdBlankError();
            return;
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            mRegisterView.hideProgress();
            mRegisterView.pwdConfirmBlankError();
            return;
        } else if (!password.equals(passwordConfirm)) {
            mRegisterView.hideProgress();
            mRegisterView.pwdNotEqualError();
            return;
        } else if (TextUtils.isEmpty(userName)) {
            mRegisterView.hideProgress();
            mRegisterView.userNameBlankError();
            return;
        } else if (TextUtils.isEmpty(code)) {
            mRegisterView.hideProgress();
            mRegisterView.codeBlankError();
            return;
        } else if (Util.illegalCharactersCheck(account)) {
            mRegisterView.hideProgress();
            mRegisterView.accountIllegalError();
            return;
        }
        SMSSDKManager.getInstance().verifyCode(mContext, "86", account, code, new SMSSDKManager.Callback() {
            @Override
            public void success() {
                RetrofitService.register(bean)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                mRegisterView.showProgress();
                            }
                        }).subscribe(new Subscriber<ResultBean<PersonBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mRegisterView.hideProgress();
                        mRegisterView.networkError();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(ResultBean<PersonBean> resultBean) {
                        if (resultBean.getStatus().equals("false")) {
                            if (resultBean.getResponse().equals("Have Registered!")) {
                                mRegisterView.hideProgress();
                                mRegisterView.accountExistError();
                            } else if (resultBean.getResponse().equals("Verify Wrong!")) {
                                mRegisterView.hideProgress();
                                mRegisterView.codeError();
                            } else {
                                mRegisterView.hideProgress();
                                mRegisterView.networkError();
                            }
                        } else {
                            mRegisterView.hideProgress();
                            mRegisterView.success();
                            mRegisterView.switch2Person(resultBean.getResponse());
                        }
                    }
                });
            }

            @Override
            public void error(Throwable error) {

            }
        });

    }

    @Override
    public void destroy() {
        mRegisterView = null;
    }


    @Override
    public void onAccountIllegalError() {

    }

    @Override
    public void onAccountBlankError() {

    }

    @Override
    public void onUserNameBlankError() {

    }

    @Override
    public void onPwdBlankError() {

    }

    @Override
    public void onPwdConfirmBlankError() {

    }

    @Override
    public void onPwdNotEqualError() {

    }

    @Override
    public void onCodeBlankError() {

    }

    @Override
    public void onCodeError() {

    }

    @Override
    public void onAccountExistError() {

    }

    @Override
    public void onSuccess(PersonBean personBean) {

    }

    @Override
    public void onNetworkError() {

    }


}