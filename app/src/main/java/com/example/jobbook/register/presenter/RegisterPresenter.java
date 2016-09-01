package com.example.jobbook.register.presenter;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterPresenter {

    void registerCheck(String account,String userName, String email, String password,
                       String passwordConfirm, String code);

    void destroy();

}
