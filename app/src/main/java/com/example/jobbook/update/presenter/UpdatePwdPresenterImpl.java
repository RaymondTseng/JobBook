package com.example.jobbook.update.presenter;

import com.example.jobbook.update.model.UpdateModel;
import com.example.jobbook.update.model.UpdateModelImpl;
import com.example.jobbook.update.view.UpdatePwdView;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePwdPresenterImpl implements UpdatePwdPresenter, UpdateModelImpl.OnUpdatePwdListener {

    private UpdateModel model;
    private UpdatePwdView view;

    public UpdatePwdPresenterImpl(UpdatePwdView view) {
        this.view = view;
        model = new UpdateModelImpl();
    }

    @Override
    public void updatePwd(String account, String oPwd, String nPwd, String nPwdConfirm) {
        model.updatePwd(account, oPwd, nPwd, nPwdConfirm, this);
    }

    @Override
    public void onUpdatePwdSuccess() {
        view.success();
        view.close();
    }

    @Override
    public void onUpdatePwdFailure() {
        view.networkError();
    }

    @Override
    public void onOriginalPwdError() {
        view.oPwdError();
    }

    @Override
    public void onOriginalPwdEqualNewPwdError() {
        view.oPwdEqualnPwdError();
    }

    @Override
    public void onOriginalPwdBlankError() {
        view.oPwdBlankError();
    }

    @Override
    public void onNewPwdBlankError() {
        view.nPwdBlankError();
    }

    @Override
    public void onConfirmPwdBlankError() {
        view.nPwdConfirmBlankError();
    }

    @Override
    public void onPwdConfirmError() {
        view.pwdConfirmError();
    }
}
