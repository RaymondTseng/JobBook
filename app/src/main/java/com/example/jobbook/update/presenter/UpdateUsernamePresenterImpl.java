package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.update.view.UpdateUsernameView;

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
        view.showProgress();
        if (TextUtils.isEmpty(username)) {
            view.hideProgress();
            view.usernameBlankError();
            return;
        } else {
            RetrofitService.updateUserName(account, username)
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        public IBaseView getBaseView() {
                            return view;
                        }

                        @Override
                        public void onNext(String s) {
                            view.success();
                            view.close();
                        }
                    });
        }
    }
}
