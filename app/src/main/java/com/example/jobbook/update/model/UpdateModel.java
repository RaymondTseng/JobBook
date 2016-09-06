package com.example.jobbook.update.model;

import android.content.Context;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdateModel {

    void updatePwd();

    void updatePhone(Context mContext, String tel, String code, UpdateModelImpl.OnUpdatePhoneListener listener);

    void updateUserName();
}
