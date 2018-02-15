package com.example.jobbook.presenter.account;

import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.account.LoginContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.Util;

/**
 * Created by zhaoxuzhang on 2018/1/27.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        attach(view);
    }

    @Override
    public void loginCheck(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            mView.setAccountError();
            return;
        } else if (TextUtils.isEmpty(password)) {
            mView.setPasswordError();
            return;
        }
        PersonWithDeviceTokenBean personBean = new PersonWithDeviceTokenBean();
        personBean.setAccount(account);
        personBean.setPassword(Util.getMD5(password));
        personBean.setDevicetoken(MyApplication.mDevicetoken);
        RetrofitService.login(personBean)
                .subscribe(new BaseSubscriber<PersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(PersonBean personBean) {
                        mView.switch2Person(personBean);
                    }
                });
    }

    @Override
    public void destroy() {
        mView = null;
    }
}
