package com.example.jobbook.person.view;

/**
 * Created by Xu on 2016/9/5.
 */
public interface UploadView {

    void showProgress();

    void hideProgress();

    void uploadSuccess();

    void uploadFailure();

    void loadHead();

}
