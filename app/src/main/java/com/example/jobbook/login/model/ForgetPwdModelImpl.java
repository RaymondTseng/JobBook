package com.example.jobbook.login.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.jobbook.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.jude.smssdk_mob.Callback;
import com.jude.smssdk_mob.SMSManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdModelImpl implements ForgetPwdModel {

    @Override
    public void checkAccount(String phone, final OnCheckAccountListener listener) {
        if(TextUtils.isEmpty(phone)){
            listener.onPhoneBlankError();
        }else{
            OkHttpUtils.get().url(Urls.CHECK_ACCOUNT_URL + phone).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onCheckAccountFailure(0);
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i("forgetpwd", "result:" + response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if(resultBean.getStatus().equals("true")){
                        listener.onCheckAccountSuccess();
                    }else{
                        listener.onCheckAccountFailure(1);
                    }
                }
            });
        }
    }

    @Override
    public void checkCode(Context mContext, String code, String phone, final OnCheckCodeListener listener) {
        if(TextUtils.isEmpty(code)){
            listener.onCodeBlankError();
        }else{
            SMSManager.getInstance().verifyCode(mContext, "86", phone, code, new Callback() {
                @Override
                public void success() {
                    listener.onCheckCodeSuccess();
                }

                @Override
                public void error(Throwable error) {
                    listener.onCheckCodeFailure();
                }
            });
        }
    }

    @Override
    public void complete(String account, String password, String confirm, final OnCompleteListener listener) {
        if(TextUtils.isEmpty(account)){
            Log.i("forgetpwd", "未知错误");
        }else if(TextUtils.isEmpty(password)){
            listener.onPasswordBlankError();
        }else if(TextUtils.isEmpty(confirm)){
            listener.onConfirmBlankError();
        }else if(!TextUtils.equals(password, confirm)){
            listener.onDifferentError();
        }else{
            OkHttpUtils.get().url(Urls.CHANGE_PWD_URL + account + "/newpsw/" + password).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onCompleteFailure();
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i("forgetpwd", "result:" + response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if(resultBean.getStatus().equals("true")){
                        listener.onCompleteSuccess();
                    }else{
                        listener.onCompleteFailure();
                    }
                }
            });
        }
    }

    public interface OnCheckAccountListener{
        void onCheckAccountSuccess();
        void onCheckAccountFailure(int error);
        void onPhoneBlankError();
    }

    public interface OnCheckCodeListener{
        void onCheckCodeSuccess();
        void onCheckCodeFailure();
        void onCodeBlankError();
    }

    public interface OnCompleteListener{
        void onCompleteSuccess();
        void onCompleteFailure();
        void onPasswordBlankError();
        void onConfirmBlankError();
        void onDifferentError();
    }
}
