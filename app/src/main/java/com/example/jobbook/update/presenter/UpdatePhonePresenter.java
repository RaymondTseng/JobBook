package com.example.jobbook.update.presenter;

import android.content.Context;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdatePhonePresenter {
    void complete(Context mContext, String account, String tel, String code);
}
