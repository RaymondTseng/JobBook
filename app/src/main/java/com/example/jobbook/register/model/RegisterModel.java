package com.example.jobbook.register.model;


import android.content.Context;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterModel {

    void register(Context mContext, String account, String userName, String password,
                  String passwordConfirm, String code, RegisterModelImpl.OnRegisterFinishedListener listener);


}
