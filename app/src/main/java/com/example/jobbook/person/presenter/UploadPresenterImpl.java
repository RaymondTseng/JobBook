package com.example.jobbook.person.presenter;

import android.graphics.Bitmap;
import com.example.jobbook.person.model.UploadModel;
import com.example.jobbook.person.model.UploadModelImpl;
import com.example.jobbook.person.view.UploadView;

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
    public void onSuccess(Bitmap bm) {
        view.hideProgress();
        view.uploadSuccess();
        view.loadHead(bm);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
        view.uploadFailure();
    }
}
