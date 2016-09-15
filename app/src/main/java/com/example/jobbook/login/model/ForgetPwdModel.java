package com.example.jobbook.login.model;

import android.content.Context;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdModel {
    void checkAccount(String phone, ForgetPwdModelImpl.OnCheckAccountListener listener);

    void checkCode(Context mContext, String code, String phone, ForgetPwdModelImpl.OnCheckCodeListener listener);

    void complete(String account, String password, String confirm, ForgetPwdModelImpl.OnCompleteListener listener);
}
