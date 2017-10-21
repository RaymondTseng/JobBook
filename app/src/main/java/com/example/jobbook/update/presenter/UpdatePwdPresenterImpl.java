package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.MyApplication;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.update.view.UpdatePwdView;
import com.example.jobbook.util.Util;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePwdPresenterImpl implements UpdatePwdPresenter {

    private UpdatePwdView view;

    public UpdatePwdPresenterImpl(UpdatePwdView view) {
        this.view = view;
    }

    @Override
    public void updatePwd(Context context, String account, String oPwd, String nPwd, String nPwdConfirm) {
        view.showProgress();
        if (TextUtils.isEmpty(oPwd)) {
            view.hideProgress();
            view.oPwdBlankError();
        } else if (TextUtils.isEmpty(nPwd)) {
            view.hideProgress();
            view.nPwdBlankError();
        } else if (TextUtils.isEmpty(nPwdConfirm)) {
            view.hideProgress();
            view.nPwdConfirmBlankError();
        } else if (!Util.getMD5(oPwd).equals(MyApplication.getmPersonBean().getPassword())) {
//            L.i("update_pwd", Util.getMD5(oPwd));
//            L.i("update_pwd", MyApplication.getmPersonBean().getPassword());
            view.hideProgress();
            view.oPwdError();
        } else if (!nPwd.equals(nPwdConfirm)) {
            view.hideProgress();
            view.pwdConfirmError();
        } else if (Util.getMD5(oPwd).equals(Util.getMD5(nPwd))) {
            view.hideProgress();
            view.oPwdEqualnPwdError();
        } else {
            RetrofitService.updatePwd(account, oPwd, nPwd)
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
                            view.networkError();
                        }

                        @Override
                        public void onNext(String s) {
                            view.hideProgress();
                            view.success();
                            view.close();
                        }
                    });
        }
    }

}
