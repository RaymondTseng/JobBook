package com.example.jobbook.register.view;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterView {

    /**
     * 登录成功
     */
    void success();

    /**
     * 隐藏进度
     */
    void hideProgress();

    /**
     * 网络错误
     */
    void networkError();

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
     * 用户名已存在错误
     */
    void accountExistError();

    /**
     * 跳转至Person界面
     */
    void switch2Person();

    /**
     * 跳转至登录界面
     */
    void switch2Login();

//    void changeViewPager();
}
