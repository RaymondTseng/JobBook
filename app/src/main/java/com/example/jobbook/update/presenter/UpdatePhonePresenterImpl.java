package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.update.view.UpdatePhoneView;
import com.example.jobbook.util.SMSSDKManager;

import rx.AsyncEmitter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhonePresenterImpl implements UpdatePhonePresenter {
    private UpdatePhoneView mView;

    public UpdatePhonePresenterImpl(UpdatePhoneView view) {
        this.mView = view;
    }

    @Override
    public void complete(Context mContext, final String account, final String tel, String code) {
        if (TextUtils.isEmpty(tel)) {
            mView.hideProgress();
            mView.newPhoneBlankError();
            return;
        } else if (TextUtils.isEmpty(code)) {
            mView.hideProgress();
            mView.codeBlankError();
            return;
        } else {
            naiveObserveVerifyCode(mContext, account, tel, code)
                    .observeOn(Schedulers.computation())
                    .flatMap(new Func<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            return RetrofitService.updateTel(account, tel);
                        }
                    })
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mView.showProgress();
                        }
                    }).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    mView.hideProgress();
                    mView.networkError();
                }

                @Override
                public void onNext(String s) {
                    mView.hideProgress();
                    mView.success();
                    mView.close();
                }
            });
        }

//            SMSSDKManager.getInstance().verifyCode(mContext, "86", tel, code, new SMSSDKManager.Callback() {
//                @Override
//                public void success() {
//                    OkHttpUtils.get().url(Urls.UPDATE_PHONE_URL + "account/" + account + "/newTel/" + tel).build().execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            listener.onUpdatePhoneFailure();
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            L.i("update_phone", response);
//                            ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
//                            if (resultBean.getStatus().equals("true")) {
//                                PersonBean personBean = new Gson().fromJson((String) resultBean.getResponse(), PersonBean.class);
//                                MyApplication.setmPersonBean(mContext, personBean);
//                                listener.onUpdatePhoneSuccess();
//                            } else {
//                                listener.onUpdatePhoneFailure();
//                            }
//                        }
//                    });
//
//                }
//
//                @Override
//                public void error(Throwable error) {
//                    listener.onCodeError();
//                }
//            });
    }

    Observable<String> naiveObserveVerifyCode(final Context mContext, final String country, final String tel, final String code) {
        return Observable.fromEmitter(new Action1<AsyncEmitter<String>>() {
            @Override
            public void call(final AsyncEmitter<String> asyncEmitter) {
                final SMSSDKManager.Callback callback = new SMSSDKManager.Callback() {
                    @Override
                    public void success() {
                        asyncEmitter.onNext("");
                    }

                    @Override
                    public void error(Throwable error) {

                    }
                };
                asyncEmitter.setCancellation(new AsyncEmitter.Cancellable() {
                    @Override
                    public void cancel() throws Exception {

                    }
                });
                SMSSDKManager.getInstance().verifyCode(mContext, country, tel, code, callback);
            }
        }, AsyncEmitter.BackpressureMode.BUFFER);
    }
}
