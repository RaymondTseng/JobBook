package com.example.jobbook.login.presenter;

import android.content.Context;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdFirstPresenter {
    void checkAccount(String phone);

    void next(Context mContext, String code, String phone);
}
