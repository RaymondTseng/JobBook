package com.example.jobbook.login.model;

import android.text.TextUtils;
import com.example.jobbook.util.L;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/7.
 */
public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String account, final String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(account)) {
            listener.onAccountError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
        }else{
            PersonBean personBean = new PersonBean();
            personBean.setAccount(account);
            personBean.setPassword(password);
            OkHttpUtils.postString().url(Urls.LOGIN_URL).content(new Gson().toJson(personBean)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.i("response", e.toString());
                    listener.onNetWorkError();
                }

                @Override
                public void onResponse(String response, int id) {
                    L.i("response", response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if(resultBean.getStatus().equals("true")){
                        PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                        listener.onSuccess(personBean);
                    }else{
                        if (resultBean.getResponse().equals("Error!")) {
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
