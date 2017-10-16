package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.update.view.UpdateUsernameView;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateUsernamePresenterImpl implements UpdateUsernamePresenter {

    private UpdateUsernameView view;

    public UpdateUsernamePresenterImpl(UpdateUsernameView view) {
        this.view = view;
    }

    @Override
    public void update(Context context, String account, String username) {
        if (TextUtils.isEmpty(username)) {
            view.hideProgress();
            view.usernameBlankError();
            return;
        } else {
            RetrofitService.updateUserName(account, username)
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
                        public void onError(Throwable e) {
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
