package com.example.jobbook.update.model;

import android.content.Context;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdateModel {

    void updatePwd(Context context, String account, String oPwd, String nPwd, String nPwdConfirm, UpdateModelImpl.OnUpdatePwdListener listener);

    void updatePhone(Context mContext, String account, String tel, String code, UpdateModelImpl.OnUpdatePhoneListener listener);

    void updateUserName(Context context, String account, String username, UpdateModelImpl.OnUpdateUserNameListener listener);
}
