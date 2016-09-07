package com.example.jobbook.update.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.Util;
import com.google.gson.Gson;
import com.jude.smssdk_mob.Callback;
import com.jude.smssdk_mob.SMSManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateModelImpl implements UpdateModel {

    @Override
    public void updatePwd(String account, String oPwd, String nPwd, String nPwdConfirm, final OnUpdatePwdListener listener) {
        if (TextUtils.isEmpty(oPwd)) {
            listener.onOriginalPwdBlankError();
        } else if (TextUtils.isEmpty(nPwd)) {
            listener.onNewPwdBlankError();
        } else if (TextUtils.isEmpty(nPwdConfirm)) {
            listener.onConfirmPwdBlankError();
        } else if (!Util.getMD5(oPwd).equals(MyApplication.getmPersonBean().getPassword())) {
            listener.onOriginalPwdError();
        } else if (!nPwd.equals(nPwdConfirm)) {
            listener.onPwdConfirmError();
        } else if (Util.getMD5(oPwd).equals(Util.getMD5(nPwd))) {
            listener.onOriginalPwdEqualNewPwdError();
        } else {
            OkHttpUtils.get().url(Urls.UPDATE_PWD_URL + "account/" + account + "/oldpsw/" + Util.getMD5(oPwd) + "/newpsw/" + Util.getMD5(nPwd)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i("response", e.getMessage());
                    listener.onUpdatePwdFailure();
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response != null && !response.equals("false")) {
                        PersonBean personBean = new Gson().fromJson(response, PersonBean.class);
                        MyApplication.setmPersonBean(personBean);
                        listener.onUpdatePwdSuccess();
                    } else {
                        listener.onUpdatePwdFailure();
                    }
                }
            });
        }
    }

    @Override
    public void updatePhone(Context mContext, final String account, final String tel, String code, final OnUpdatePhoneListener listener) {
        if (TextUtils.isEmpty(tel)) {
            listener.onNewPhoneBlankError();
        } else if (TextUtils.isEmpty(code)) {
            listener.onCodeBlankError();
        } else {
            SMSManager.getInstance().verifyCode(mContext, "86", tel, code, new Callback() {
                @Override
                public void success() {
                    OkHttpUtils.get().url(Urls.UPDATE_PHONE_URL + "account/" + account + "/newTel/" + tel).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onUpdatePhoneFailure();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response != null && !response.equals("false")) {
                                PersonBean personBean = new Gson().fromJson(response, PersonBean.class);
                                MyApplication.setmPersonBean(personBean);
                                listener.onUpdatePhoneSuccess();
                            }else {
                                listener.onUpdatePhoneFailure();
                            }
                        }
                    });

                }

                @Override
                public void error(Throwable error) {
                    listener.onCodeError();
                }
            });
        }
    }

    @Override
    public void updateUserName(String account, String username, final OnUpdateUserNameListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.onUserNameBlankError();
        } else {
            OkHttpUtils.get().url(Urls.UPDATE_USERNAME_URL + "account/" + account + "/newName/" + username).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    listener.onUpdateUserNameFailure();
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response != null && !response.equals("false")) {
                        PersonBean personBean = new Gson().fromJson(response, PersonBean.class);
                        MyApplication.setmPersonBean(personBean);
                        listener.onUpdateUserNameSuccess();
                    } else {
                        listener.onUpdateUserNameFailure();
                    }
                }
            });
        }
    }


    public interface OnUpdatePhoneListener {
        void onUpdatePhoneSuccess();

        void onUpdatePhoneFailure();

        void onCodeError();

        void onCodeBlankError();

        void onNewPhoneBlankError();
    }

    public interface OnUpdatePwdListener {
        void onUpdatePwdSuccess();

        void onUpdatePwdFailure();

        void onOriginalPwdError();

        void onOriginalPwdEqualNewPwdError();

        void onOriginalPwdBlankError();

        void onNewPwdBlankError();

        void onConfirmPwdBlankError();

        void onPwdConfirmError();
    }

    public interface OnUpdateUserNameListener {
        void onUpdateUserNameSuccess();

        void onUpdateUserNameFailure();

        void onUserNameBlankError();
    }
}
