package com.example.jobbook.login.model;

import android.text.TextUtils;
import android.util.Log;

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
                    Log.i("response", e.toString());
                    listener.onNetWorkError();
                }

                @Override
                public void onResponse(String response, int id) {
                    if(!TextUtils.isEmpty(response)){
                        Log.i("login_response", response.toString());
                        PersonBean personBean = new Gson().fromJson(response, PersonBean.class);
                        if(personBean != null){
                            if(personBean.getAccount().equals("Error!")){
                                listener.onUserError();
                            }else{
                                Log.i("response", "login successful");
                                listener.onSuccess(personBean);
                            }
                        }else{
                            listener.onNetWorkError();
                        }
                    }else{

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
