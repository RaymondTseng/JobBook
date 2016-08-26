package com.example.jobbook.register.model;


/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterModel {

    void register(String account, String password, String passwordConfirm, RegisterModelImpl.OnRegisterFinishedListener listener);
}
