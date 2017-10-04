package com.example.jobbook.person.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.UploadView;

import okhttp3.MultipartBody;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadPresenterImpl implements UploadPresenter {

    private UploadView view;

    public UploadPresenterImpl(UploadView view) {
        this.view = view;
    }

    @Override
    public void uploadAvatar(MultipartBody.Part pic) {
        String account = MyApplication.getAccount();
        RetrofitService.uploadAvatar(account, pic)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showProgress();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        view.hideProgress();
                        view.uploadFailure();
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        view.hideProgress();
                        view.uploadSuccess();
                        view.loadHead();
                    }
                });
    }

}
