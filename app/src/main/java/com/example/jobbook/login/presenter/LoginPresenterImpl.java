package com.example.jobbook.login.presenter;

import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.model.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.login.view.LoginView;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
    }

    @Override
    public void loginCheck(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            mLoginView.setAccountError();
            return;
        } else if (TextUtils.isEmpty(password)) {
            mLoginView.setPasswordError();
            return;
        }
        PersonWithDeviceTokenBean personBean = new PersonWithDeviceTokenBean();
        personBean.setAccount(account);
        personBean.setPassword(Util.getMD5(password));
        personBean.setDevicetoken(MyApplication.mDevicetoken);
        RetrofitService.login(personBean)
                .subscribe(new BaseObserver<PersonBean>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mLoginView;
                    }

                    @Override
                    public void onNext(PersonBean personBean) {
                        mLoginView.switch2Person(personBean);
                    }
                });

    }

    @Override
    public void destroy() {
        mLoginView = null;
    }

}