package com.example.jobbook.login.presenter;

import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.login.view.LoginView;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.util.Util;

import rx.Subscriber;
import rx.functions.Action0;

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLoginView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean<PersonBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        mLoginView.hideProgress();
                        if (throwable.getMessage().contains("java.lang.IllegalStateException: Expected BEGIN_OBJECT")) {
                            mLoginView.setUserError();
                        } else {
                            mLoginView.setNetworkError();
                        }

                    }

                    @Override
                    public void onNext(ResultBean<PersonBean> resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mLoginView.hideProgress();
                            mLoginView.switch2Person(resultBean.getResponse());
                        } else {
                            if (resultBean.getResponse().equals("Login Error!")) {
                                mLoginView.setUserError();
                            } else {
                                mLoginView.setNetworkError();
                            }
                        }

                    }
                });

    }

    @Override
    public void destroy() {
        mLoginView = null;
    }

}