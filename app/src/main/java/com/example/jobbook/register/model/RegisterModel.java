package com.example.jobbook.register.model;

import android.net.nsd.NsdManager;

/**
 * Created by Xu on 2016/7/7.
 */
public interface RegisterModel {

    void register(String username, String password, RegisterModelImpl.OnRegisterFinishedListener listener);
}
