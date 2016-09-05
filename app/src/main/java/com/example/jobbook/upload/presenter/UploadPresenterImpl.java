package com.example.jobbook.upload.presenter;

import android.graphics.Bitmap;

import com.example.jobbook.upload.model.UploadModel;
import com.example.jobbook.upload.model.UploadModelImpl;
import com.example.jobbook.upload.view.UploadView;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadPresenterImpl implements UploadPresenter, UploadModelImpl.OnUploadImageListener {

    private UploadModel model;
    private UploadView view;

    public UploadPresenterImpl(UploadView view) {
        model = new UploadModelImpl();
        this.view = view;
    }

    @Override
    public void uploadImage(Bitmap bm) {
        view.showProgress();
        model.uploadImage(bm, this);
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.uploadSuccess();
        view.uploadSuccessFinish();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
        view.uploadFailure();
        view.uploadFailureFinish();
    }
}
