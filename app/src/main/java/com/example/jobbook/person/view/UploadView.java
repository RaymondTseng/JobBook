package com.example.jobbook.person.view;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UploadView {

    void showProgress();

    void hideProgress();

    void uploadSuccess();

    void uploadFailure();

    void loadHead(Bitmap bm);

}
