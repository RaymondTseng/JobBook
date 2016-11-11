package com.example.jobbook.update.view;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdatePwdView {

    void close();

    void complete();

    void oPwdBlankError();

    void nPwdBlankError();

    void nPwdConfirmBlankError();

    void pwdConfirmError();

    void success();

    void networkError();

    void oPwdError();

    void oPwdEqualnPwdError();

    void showProgress();

    void hideProgress();
}
