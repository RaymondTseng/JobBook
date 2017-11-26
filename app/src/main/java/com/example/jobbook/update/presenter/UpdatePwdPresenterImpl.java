package com.example.jobbook.update.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.update.view.UpdatePwdView;
import com.example.jobbook.util.Util;

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
                    .subscribe(new BaseSubscriber<String>() {
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
