package com.example.jobbook.login.view;

import android.content.Context;

import com.example.jobbook.base.IBaseView;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdFirstView extends IBaseView {

    void close();

    void phoneBlankError();

    void codeBlankError();

    void checkSuccess();

    void checkFailure(int error);

    void codeSuccess();

    void codeFailure();

    void checkAccount();

    void next(Context mContext);
}
