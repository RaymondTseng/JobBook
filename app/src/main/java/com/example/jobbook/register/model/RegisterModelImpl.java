package com.example.jobbook.register.model;

import android.text.TextUtils;
import android.util.Log;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/7.
 */
public class RegisterModelImpl implements RegisterModel {

    @Override
    public void register(final String username, final String password, final OnRegisterFinishedListener listener) {
        PersonBean personBean = new PersonBean();
        personBean.setUsername(username);
        personBean.setPassword(password);
        OkHttpUtils.postString().url(Urls.REGISTER_URL).content(new Gson().toJson(personBean)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i("response", e.toString());
                listener.onNetworkError();
            }

            @Override
            public void onResponse(String response, int id) {
                if (!TextUtils.isEmpty(response)) {
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        Log.i("receive", "response is true");
                        listener.onSuccess();
                    }else {
                        Log.i("receive", "response is false");
                    }
                } else {
                    Log.i("receive", "error");
                }

            }
        });
    }

    public interface OnRegisterFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onNetworkError();
    }
}
