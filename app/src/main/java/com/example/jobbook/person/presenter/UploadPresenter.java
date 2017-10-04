package com.example.jobbook.person.presenter;

import okhttp3.MultipartBody;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UploadPresenter {

    void uploadAvatar(MultipartBody.Part pic);
}
