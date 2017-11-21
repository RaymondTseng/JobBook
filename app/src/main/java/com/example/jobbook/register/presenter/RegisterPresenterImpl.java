package com.example.jobbook.register.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.register.view.RegisterView;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;


public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView view) {
        mRegisterView = view;
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
                        .subscribe(new BaseObserver<PersonBean>() {
                            @Override
                            public IBaseView getBaseView() {
                                return mRegisterView;
                            }

                            @Override
                            public void onNext(PersonBean personBean) {
                                mRegisterView.hideProgress();
                                mRegisterView.success();
                                mRegisterView.switch2Person(personBean);
                            }
                        });
            }

            @Override
            public void error(Throwable error) {
                mRegisterView.hideProgress();
                mRegisterView.codeError();
            }
        });
    }

    @Override
    public void destroy() {
        mRegisterView = null;
    }

}