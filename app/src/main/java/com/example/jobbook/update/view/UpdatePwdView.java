package com.example.jobbook.update.view;

import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdatePwdView extends IBaseView {

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

}