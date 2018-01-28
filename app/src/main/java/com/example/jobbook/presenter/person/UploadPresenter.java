package com.example.jobbook.presenter.person;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UploadContract;
import com.example.jobbook.model.http.RetrofitService;

import okhttp3.MultipartBody;

/**
 * Created by Xu on 2018/1/28.
 */

public class UploadPresenter extends RxPresenter<UploadContract.View> implements UploadContract.Presenter {

    public UploadPresenter(UploadContract.View view) {
        attach(view);
    }

    @Override
    public void uploadAvatar(MultipartBody.Part pic) {
        String account = MyApplication.getAccount();
        RetrofitService.uploadAvatar(account, pic)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.uploadSuccess();
                        mView.loadHead();
                    }
                });
    }
}
