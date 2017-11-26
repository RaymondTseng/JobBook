package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.update.view.UpdatePhoneView;
import com.example.jobbook.util.SMSSDKManager;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
        mView.showProgress();
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
                    .flatMap(new Function<String, Flowable<String>>() {
                        @Override
                        public Flowable<String> apply(String s) throws Exception {
                            return RetrofitService.updateTel(account, tel);
                        }
                    })
                    .subscribe(new BaseSubscriber<String>() {
                        @Override
                        public IBaseView getBaseView() {
                            return mView;
                        }

                        @Override
                        public void onNext(String s) {
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

    Flowable<String> naiveObserveVerifyCode(final Context mContext, final String country, final String tel, final String code) {
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(final FlowableEmitter<String> e) throws Exception {
                final SMSSDKManager.Callback callback = new SMSSDKManager.Callback() {
                    @Override
                    public void success() {
                        e.onNext("");
                    }

                    @Override
                    public void error(Throwable error) {

                    }
                };
                SMSSDKManager.getInstance().verifyCode(mContext, country, tel, code, callback);
            }
        }, BackpressureStrategy.LATEST);
    }
}
