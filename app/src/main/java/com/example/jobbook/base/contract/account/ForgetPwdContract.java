package com.example.jobbook.base.contract.account;

import android.content.Context;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2017/12/17.
 */

public interface ForgetPwdContract {

    interface ForgetPwdFirstView extends IBaseView {
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

    interface ForgetPwdFirstPresenter extends IBasePresenter<ForgetPwdFirstView> {
        void checkAccount(String phone);

        void next(Context mContext, String code, String phone);
    }

    interface ForgetPwdSecondView extends IBaseView {
        void phoneBlankError();

        void confirmPhoneBlankError();

        void differentError();

        void success();

        void failure();

        void complete();
    }

    interface ForgetPwdSecondPresenter extends IBasePresenter<ForgetPwdSecondView> {
        void complete(String account, String password, String confirm);
    }
}
