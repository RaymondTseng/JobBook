package com.example.jobbook.login.view;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdSecondView {
    void showProgress();

    void hideProgress();

    void phoneBlankError();

    void confirmPhoneBlankError();

    void differentError();

    void success();

    void failure();

    void complete();
}
