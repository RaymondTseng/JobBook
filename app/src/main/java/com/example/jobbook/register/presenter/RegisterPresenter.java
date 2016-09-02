package com.example.jobbook.register.presenter;

import android.content.Context;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterPresenter {

    void registerCheck(Context mContext, String account,String userName, String email, String password,
                       String passwordConfirm, String code);

    void destroy();


}
