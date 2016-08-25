package com.example.jobbook.register.presenter;

import android.widget.Toast;

import com.example.jobbook.register.model.RegisterModel;
import com.example.jobbook.register.model.RegisterModelImpl;
import com.example.jobbook.register.view.RegisterView;

/**
 * Created by Xu on 2016/7/7.
 */
public class RegisterPresenterImpl implements RegisterPresenter, RegisterModelImpl.OnRegisterFinishedListener{

    private RegisterModel mRegisterModel;
    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView view) {
        mRegisterView = view;
        mRegisterModel = new RegisterModelImpl();
    }

    @Override
    public void registerCheck(String username, String password) {
        mRegisterModel.register(username, password, this);
    }

    @Override
    public void destroy() {
        mRegisterView = null;
    }

    @Override
    public void onUsernameError() {
        mRegisterView.setUserNameError();
    }

    @Override
    public void onPasswordError() {
        mRegisterView.setPwdError();
    }

    @Override
    public void onSuccess() {
        mRegisterView.switch2Person();
    }

    @Override
    public void onNetworkError() {
        mRegisterView.setNetworkError();
    }
}
