package com.example.jobbook.login.presenter;

import android.text.TextUtils;

import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.login.view.ForgetPwdSecondView;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.util.L;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdSecondPresenterImpl implements ForgetPwdSecondPresenter {
    private ForgetPwdSecondView mView;
//    private ForgetPwdModel mModel;

    public ForgetPwdSecondPresenterImpl(ForgetPwdSecondView mView) {
        this.mView = mView;
//        mModel = new ForgetPwdModelImpl();
    }

    @Override
    public void complete(String account, String password, String confirm) {
//        mView.showProgress();
//        mModel.complete(account, password, confirm, this);
        if (TextUtils.isEmpty(account)) {
            L.i("forgetpwd", "未知错误");
        } else if (TextUtils.isEmpty(password)) {
            mView.hideProgress();
            mView.phoneBlankError();
        } else if (TextUtils.isEmpty(confirm)) {
            mView.hideProgress();
            mView.confirmPhoneBlankError();
        } else if (!TextUtils.equals(password, confirm)) {
            mView.hideProgress();
            mView.differentError();
        } else {
            RetrofitService.changePwdComplete(account, password)
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showProgress();
                        }
                    })
                    .subscribe(new Subscriber<ResultBean<String>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.hideProgress();
                            mView.failure();
                        }

                        @Override
                        public void onNext(ResultBean<String> resultBean) {
                            if (resultBean.getStatus().equals("true")) {
                                mView.hideProgress();
                                mView.success();
                            } else {
                                mView.hideProgress();
                                mView.failure();
                            }
                        }
                    });
        }
    }

}
