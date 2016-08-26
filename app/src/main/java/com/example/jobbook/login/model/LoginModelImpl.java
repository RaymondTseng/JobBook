package com.example.jobbook.login.model;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String account, String password, OnLoginFinishedListener listener) {
        PersonBean personBean = new PersonBean();
        personBean.setAccount(account);
        personBean.setPassword(password);
        OkHttpUtils.postString().url(Urls.LOGIN_URL).content(new Gson().toJson(personBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

            }
        });
    }

    public interface OnLoginFinishedListener {
        void onaccountError();

        void onPasswordError();

        void onSuccess();

        void onNetWorkError();
    }
}
