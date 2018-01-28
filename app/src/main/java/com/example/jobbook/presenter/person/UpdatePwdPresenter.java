package com.example.jobbook.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.UpdateContract;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2018/1/28.
 */

public class UpdatePwdPresenter extends RxPresenter<UpdateContract.UpdatePwdView> implements UpdateContract.UpdatePwdPresenter {

    public UpdatePwdPresenter(UpdateContract.UpdatePwdView view) {
        attach(view);
    }

    @Override
    public void updatePwd(Context context, String account, String oPwd, String nPwd, String nPwdConfirm) {
        mView.showProgress();
        if (TextUtils.isEmpty(oPwd)) {
            mView.hideProgress();
            mView.oPwdBlankError();
        } else if (TextUtils.isEmpty(nPwd)) {
            mView.hideProgress();
            mView.nPwdBlankError();
        } else if (TextUtils.isEmpty(nPwdConfirm)) {
            mView.hideProgress();
            mView.nPwdConfirmBlankError();
        } else if (!Util.getMD5(oPwd).equals(MyApplication.getmPersonBean().getPassword())) {
            mView.hideProgress();
            mView.oPwdError();
        } else if (!nPwd.equals(nPwdConfirm)) {
            mView.hideProgress();
            mView.pwdConfirmError();
        } else if (Util.getMD5(oPwd).equals(Util.getMD5(nPwd))) {
            mView.hideProgress();
            mView.oPwdEqualnPwdError();
        } else {
            RetrofitService.updatePwd(account, oPwd, nPwd)
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
