package com.example.jobbook.update.model;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.commons.Urls;
import com.jude.smssdk_mob.Callback;
import com.jude.smssdk_mob.SMSManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateModelImpl implements UpdateModel {

    @Override
    public void updatePwd() {

    }

    @Override
    public void updatePhone(Context mContext, String tel, String code, final OnUpdatePhoneListener listener) {
        if(TextUtils.isEmpty(tel)){
            listener.onNewPhoneBlankError();
        }else if(TextUtils.isEmpty(code)){
            listener.onCodeBlankError();
        }else{
            SMSManager.getInstance().verifyCode(mContext, "86", tel, code, new Callback() {
                @Override
                public void success() {
//                    listener.onSuccess();
                    OkHttpUtils.get().url(Urls.UPDATE_PHONE_URL).addParams("","").build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onFailure();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            listener.onSuccess();
                        }
                    });

                }

                @Override
                public void error(Throwable error) {
                    listener.onFailure();
                }
            });
        }
    }


    @Override
    public void updateUserName() {

    }

    public interface OnUpdatePhoneListener{
        void onSuccess();
        void onFailure();
        void onCodeBlankError();
        void onNewPhoneBlankError();
    }
}
