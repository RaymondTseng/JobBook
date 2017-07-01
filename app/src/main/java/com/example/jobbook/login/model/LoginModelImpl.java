package com.example.jobbook.login.model;

import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.PersonWithDeviceTokenBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String account, String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(account)) {
            listener.onAccountError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
        }else{
            PersonWithDeviceTokenBean personBean = new PersonWithDeviceTokenBean();
            personBean.setAccount(account);
            personBean.setPassword(Util.getMD5(password));
            personBean.setDevicetoken(MyApplication.mDevicetoken);
            L.i("loginmodelimpl", personBean.toString());
            OkHttpUtils.postString().url(Urls.LOGIN_URL).content(new Gson().toJson(personBean)).build().
                    execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.i("login_response", e.toString());
                    L.i("login_response", id + "");
                    listener.onNetWorkError();
                }

                @Override
                public void onResponse(String response, int id) {
                    L.i("login_response", response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if(resultBean.getStatus().equals("true")){
                        PersonBean personBean = new Gson().fromJson((String)resultBean.getResponse(), PersonBean.class);
                        listener.onSuccess(personBean);
                    }else{
                        if (resultBean.getResponse().equals("Login Error!")) {
                            listener.onUserError();
                        } else {
                            listener.onNetWorkError();
                        }
                    }
                }
            });
        }
    }

    public interface OnLoginFinishedListener {
        void onUserError();

        void onAccountError();

        void onPasswordError();

        void onSuccess(PersonBean personBean);

        void onNetWorkError();
    }
}
