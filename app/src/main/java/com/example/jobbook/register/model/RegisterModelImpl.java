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
    public void register(final String account, final String password, final String passwordConfirm, final OnRegisterFinishedListener listener) {
        if (TextUtils.isEmpty(account)) {
            listener.onAccountBlankError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onPwdBlankError();
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            listener.onPwdConfirmBlankError();
        } else if (!password.equals(passwordConfirm)) {
            listener.onPwdNotEqualError();
        } else {
            PersonBean personBean = new PersonBean();
            personBean.setAccount(account);
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
                        if (resultBean.getStatus().equals("false")) {
                            Log.i("receive", "response is true");
                            listener.onAccountExistError();
                        }
                        listener.onAccountExistError();
                    } else {
                        if (TextUtils.isEmpty(response)) {
                            Log.i("personaccount:", "null");
                        } else {
                            Log.i("personaccount:", response);
                            PersonBean personBean = new Gson().fromJson(response, PersonBean.class);
                            Log.i("personaccount:", personBean.getAccount());
                            listener.onSuccess();
                        }

                    }

                }
            });
        }
    }

    public interface OnRegisterFinishedListener {
        void onAccountBlankError();

        void onPwdBlankError();

        void onPwdConfirmBlankError();

        void onPwdNotEqualError();

        void onAccountExistError();

        void onSuccess();

        void onNetworkError();
    }
}
