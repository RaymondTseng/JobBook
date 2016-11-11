package com.example.jobbook.register.model;

import android.content.Context;
import android.text.TextUtils;
import com.example.jobbook.util.L;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.google.gson.Gson;
import com.jude.smssdk_mob.Callback;
import com.jude.smssdk_mob.SMSManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/7/7.
 */
public class RegisterModelImpl implements RegisterModel {

    @Override
    public void register(Context mContext, final String account, final String userName, final String password,
                         final String passwordConfirm, final String code, final OnRegisterFinishedListener listener) {
        if (TextUtils.isEmpty(account)) {
            listener.onAccountBlankError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onPwdBlankError();
        } else if (TextUtils.isEmpty(passwordConfirm)) {
            listener.onPwdConfirmBlankError();
        } else if (!password.equals(passwordConfirm)) {
            listener.onPwdNotEqualError();
        } else if (TextUtils.isEmpty(userName)) {
            listener.onUserNameBlankError();
        } else if (TextUtils.isEmpty(code)) {
            listener.onCodeBlankError();
        } else if (Util.illegalCharactersCheck(account)) {
            listener.onAccountIllegalError();
        } else {
            SMSManager.getInstance().verifyCode(mContext, "86", account, code, new Callback() {
                @Override
                public void success() {
                    PersonBean personBean = new PersonBean();
                    personBean.setAccount(account);
                    personBean.setPassword(password);
                    personBean.setUsername(userName);
                    personBean.setTelephone(null);
                    L.i("registermodelimpl", Urls.REGISTER_URL);
                    OkHttpUtils.postString().url(Urls.REGISTER_URL).content(new Gson().
                            toJson(personBean)).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            L.i("response", e.toString());
                            listener.onNetworkError();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                            if (resultBean.getStatus().equals("true")) {
                                PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                                listener.onSuccess(personBean);
                            } else {
                                if (resultBean.getResponse().equals("Have Registered!")) {
                                    listener.onAccountExistError();
                                } else if (resultBean.getResponse().equals("Verify Wrong!")) {
                                    listener.onCodeError();
                                } else {
                                    listener.onNetworkError();
                                }
                            }

                        }
                    });
                }

                @Override
                public void error(Throwable error) {
                    L.i("register", "code error");
                    listener.onCodeError();
                }
            });
        }
    }


    public interface OnRegisterFinishedListener {
        void onAccountIllegalError();

        void onAccountBlankError();

        void onUserNameBlankError();

        void onPwdBlankError();

        void onPwdConfirmBlankError();

        void onPwdNotEqualError();

        void onCodeBlankError();

        void onCodeError();

        void onAccountExistError();

        void onSuccess(PersonBean personBean);

        void onNetworkError();

    }
}