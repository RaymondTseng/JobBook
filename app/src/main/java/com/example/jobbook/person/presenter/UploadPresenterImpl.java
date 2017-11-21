package com.example.jobbook.person.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.UploadView;

import okhttp3.MultipartBody;

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
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return view;
                    }

                    @Override
                    public void onNext(String s) {
                        view.uploadSuccess();
                        view.loadHead();
                    }
                });
    }

}
