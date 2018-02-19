package com.example.jobbook.presenter.account;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.account.RegisterContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2018/1/28.
 */

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterContract.View view) {
        attach(view);
    }

    @Override
    public void registerCheck(Context mContext, String account, String userName, String password, String passwordConfirm, String code) {
        final PersonWithDeviceTokenBean bean = new PersonWithDeviceTokenBean();
        if (TextUtils.isEmpty(account)) {
            mView.hideProgress();
            mView.accountBlankError();
            return;
        } else if (TextUtils.isEmpty(password)) {
            mView.hideProgress();
            mView.pwdBlankError();
            return;
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            mView.hideProgress();
            mView.pwdConfirmBlankError();
            return;
        } else if (!password.equals(passwordConfirm)) {
            mView.hideProgress();
            mView.pwdNotEqualError();
            return;
        } else if (TextUtils.isEmpty(userName)) {
            mView.hideProgress();
            mView.userNameBlankError();
            return;
        } else if (TextUtils.isEmpty(code)) {
            mView.hideProgress();
            mView.codeBlankError();
            return;
        } else if (Util.illegalCharactersCheck(account)) {
            mView.hideProgress();
            mView.accountIllegalError();
            return;
        }
        bean.setAccount(account);
        bean.setUsername(userName);
        bean.setPassword(password);
        bean.setDevicetoken(MyApplication.mDevicetoken);
        // todo 注册逻辑优化！
        SMSSDKManager.getInstance().verifyCode(mContext, "86", account, code, new SMSSDKManager.Callback() {
            @Override
            public void success() {
                RetrofitService.register(bean)
                        .subscribe(new BaseSubscriber<PersonBean>() {
                            @Override
                            public IBaseView getBaseView() {
                                return mView;
                            }

                            @Override
                            public void onNext(PersonBean personBean) {
                                mView.success();
                                mView.switch2Person(personBean);
                            }
                        });
            }

            @Override
            public void error(Throwable error) {
                mView.hideProgress();
                mView.codeError();
            }
        });
    }

}
