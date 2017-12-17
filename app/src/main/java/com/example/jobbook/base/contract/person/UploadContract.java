package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;

import okhttp3.MultipartBody;

/**
 * Created by Xu on 2017/12/17.
 */

public interface UploadContract {

    interface View extends IBaseView {
        void uploadSuccess();

        void loadHead();
    }

    interface Presenter extends IBasePresenter<View> {
        void uploadAvatar(MultipartBody.Part pic);
    }
}
