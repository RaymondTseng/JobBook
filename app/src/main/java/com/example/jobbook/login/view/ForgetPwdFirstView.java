package com.example.jobbook.login.view;

import android.content.Context;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdFirstView {
    void showProgress();

    void hideProgress();

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
