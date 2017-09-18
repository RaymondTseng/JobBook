package com.example.jobbook.login.view;

import com.example.jobbook.base.IBaseView;

/**
 * Created by 椰树 on 2016/9/14.
 */
public interface ForgetPwdSecondView extends IBaseView {

    void phoneBlankError();

    void confirmPhoneBlankError();

    void differentError();

    void success();

    void failure();

    void complete();
}
