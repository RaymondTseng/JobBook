package com.example.jobbook.update.presenter;

import android.content.Context;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdatePwdPresenter {

    void updatePwd(Context context, String account, String oPwd, String nPwd, String nPwdConfirm);
}
