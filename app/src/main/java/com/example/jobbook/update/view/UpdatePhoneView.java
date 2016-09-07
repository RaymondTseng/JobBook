package com.example.jobbook.update.view;

import android.content.Context;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UpdatePhoneView {
    void close();

    void getCode();

    void complete(Context mContext);

    void codeBlankError();

    void codeError();

    void newPhoneBlankError();

    void success();

    void networkError();
}
