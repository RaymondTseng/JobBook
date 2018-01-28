package com.example.jobbook.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UpdateContract;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by Xu on 2018/1/28.
 */

public class UpdateUsernamePresenter extends RxPresenter<UpdateContract.UpdateUsernameView> implements UpdateContract.UpdateUsernamePresenter {

    public UpdateUsernamePresenter(UpdateContract.UpdateUsernameView view) {
        attach(view);
    }

    @Override
    public void update(Context context, String account, String username) {
        mView.showProgress();
        if (TextUtils.isEmpty(username)) {
            mView.hideProgress();
            mView.usernameBlankError();
            return;
        } else {
            RetrofitService.updateUserName(account, username)
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
    }
}
