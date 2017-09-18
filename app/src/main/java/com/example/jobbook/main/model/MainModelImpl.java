package com.example.jobbook.main.model;

import android.util.Log;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by root on 16-10-25.
 */

public class MainModelImpl implements MainModel {
    @Override
    public void loginCheck(final PersonBean personBean, final OnLoginCheckListener listener) {
        OkHttpUtils.get().url(Urls.LOGIN_CHECK_URL + personBean.getAccount()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                listener.onError();
            }

            @Override
            public void onResponse(String response, int i) {
                Log.i("logincheck", response);
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if(resultBean.getStatus().equals("true")){
                    listener.onSuccess(personBean);
                }else{
                    listener.onLoginTimeOut();
                }
            }
        });
    }


    public interface OnLoginCheckListener{
        void onSuccess(PersonBean personBean);

        void onLoginTimeOut();

        void onError();
    }
}
