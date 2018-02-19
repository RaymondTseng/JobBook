package com.example.jobbook.base.contract.account;

import android.content.Context;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;

/**
 * Created by Xu on 2017/12/17.
 */

public interface RegisterContract {

    interface View extends IBaseView {
        /**
         * 登录成功
         */
        void success();

        /**
         * 用户名为空错误
         */
        void accountBlankError();

        /**
         * 密码为空错误
         */
        void pwdBlankError();

        /**
         * 确认密码为空错误
         */
        void pwdConfirmBlankError();

        /**
         * 密码与确认密码不一致错误
         */
        void pwdNotEqualError();

        /**
         * 跳转至Person界面
         */
        void switch2Person(PersonBean personBean);

        /**
         * 账号非法字符错误
         */
        void accountIllegalError();

        /**
         * 用户名为空错误
         */
        void userNameBlankError();

        /**
         * 验证码为空
         */
        void codeBlankError();

        /**
         * 验证码错误
         */
        void codeError();
    }

    interface Presenter extends IBasePresenter<View> {
        void registerCheck(Context mContext, String account, String userName, String password,
                           String passwordConfirm, String code);
    }
}
