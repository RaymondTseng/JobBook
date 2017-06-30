package com.example.jobbook.update.model;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateModelImpl implements UpdateModel {

    @Override
    public void updatePwd(final Context context, String account, String oPwd, String nPwd, String nPwdConfirm, final OnUpdatePwdListener listener) {
        if (TextUtils.isEmpty(oPwd)) {
            listener.onOriginalPwdBlankError();
        } else if (TextUtils.isEmpty(nPwd)) {
            listener.onNewPwdBlankError();
        } else if (TextUtils.isEmpty(nPwdConfirm)) {
            listener.onConfirmPwdBlankError();
        } else if (!Util.getMD5(oPwd).equals(MyApplication.getmPersonBean().getPassword())) {
            L.i("update_pwd", Util.getMD5(oPwd));
            L.i("update_pwd", MyApplication.getmPersonBean().getPassword());
            listener.onOriginalPwdError();
        } else if (!nPwd.equals(nPwdConfirm)) {
            listener.onPwdConfirmError();
        } else if (Util.getMD5(oPwd).equals(Util.getMD5(nPwd))) {
            listener.onOriginalPwdEqualNewPwdError();
        } else {
            OkHttpUtils.get().url(Urls.UPDATE_PWD_URL + "account/" + account + "/oldpsw/" +
                    Util.getMD5(oPwd) + "/newpsw/" + Util.getMD5(nPwd)).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.i("response", e.getMessage());
                    listener.onUpdatePwdFailure();
                }

                @Override
                public void onResponse(String response, int id) {
                    L.i("update_pwd", response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                        MyApplication.setmPersonBean(context, personBean);
                        listener.onUpdatePwdSuccess();
                    } else {
                        listener.onUpdatePwdFailure();
                    }
                }
            });
        }
    }

    @Override
    public void updatePhone(final Context mContext, final String account, final String tel, String code, final OnUpdatePhoneListener listener) {
        if (TextUtils.isEmpty(tel)) {
            listener.onNewPhoneBlankError();
        } else if (TextUtils.isEmpty(code)) {
            listener.onCodeBlankError();
        } else {
            SMSSDKManager.getInstance().verifyCode(mContext, "86", tel, code, new SMSSDKManager.Callback() {
                @Override
                public void success() {
                    OkHttpUtils.get().url(Urls.UPDATE_PHONE_URL + "account/" + account + "/newTel/" + tel).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            listener.onUpdatePhoneFailure();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            L.i("update_phone", response);
                            ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                            if (resultBean.getStatus().equals("true")) {
                                PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                                MyApplication.setmPersonBean(mContext, personBean);
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
    public void updateUserName(final Context context, String account, String username, final OnUpdateUserNameListener listener) {
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
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        PersonBean personBean = new Gson().fromJson(resultBean.getResponse(), PersonBean.class);
                        MyApplication.setmPersonBean(context, personBean);
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
