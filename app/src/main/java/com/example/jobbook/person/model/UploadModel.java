package com.example.jobbook.person.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UploadModel {

    void uploadImage(Bitmap bm, UploadModelImpl.OnUploadImageListener listener);
}
