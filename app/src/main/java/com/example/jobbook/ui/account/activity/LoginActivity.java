package com.example.jobbook.ui.account.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.account.LoginContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.presenter.account.LoginPresenter;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.ui.main.activity.MainActivity;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/6/2.
 */
public class LoginActivity extends Activity implements LoginContract.View {

    @BindView(R.id.login_login_bt)
    Button mLoginButton;

    @BindView(R.id.login_register_tv)
    TextView mRegisterTextView;

    @BindView(R.id.login_close_ib)
    ImageButton mCloseImageButton;

    @BindView(R.id.login_account_et)
    EditText mAccountEditText;

    @BindView(R.id.login_password_et)
    EditText mPasswordEditText;

    @BindView(R.id.activity_login_loading)
    ViewStub mLoadingLinearLayout;

    @BindView(R.id.login_forget_tv)
    TextView mForgetPwdTextView;

    @BindView(R.id.activity_login_layout)
    LinearLayout mParentLayout;

    private LoginPresenter presenter;
    private View view;
    private MyPushIntentService.MyRefreshBinder binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyPushIntentService.MyRefreshBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        view = getWindow().getDecorView();
        initEvents();
        presenter = new LoginPresenter(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, MyPushIntentService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private void initEvents() {
        mAccountEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mPasswordEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });
        mPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    presenter.loginCheck(mAccountEditText.getText().toString(), mPasswordEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        unbindService(connection);
    }

    @OnClick(R.id.login_login_bt)
    public void login() {
        presenter.loginCheck(mAccountEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.login_register_tv)
    public void register() {
        switch2Register();
    }

    @OnClick(R.id.login_close_ib)
    public void close() {
        Bundle bundle = new Bundle();
        bundle.putString("CHANGE_TO", "JOB");
        Util.toAnotherActivity(LoginActivity.this, MainActivity.class, bundle);
        finish();
    }

    @OnClick(R.id.login_forget_tv)
    public void login_forget() {
        Util.toAnotherActivity(LoginActivity.this, ForgetPwdFirstActivity.class);
        finish();
    }

    @OnClick(R.id.activity_login_layout)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Bundle bundle = new Bundle();
            bundle.putString("CHANGE_TO", "JOB");
            Util.toAnotherActivity(LoginActivity.this, MainActivity.class, bundle);
            finish();
        }
        return false;
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {

    }

    @Override
    public void setNetworkError() {
        Util.showSnackBar(view, "网络连接错误！", "重试");
    }

    @Override
    public void setUserError() {
        Util.showSnackBar(view, "账号或密码错误");
    }

    @Override
    public void setAccountError() {
        Util.showSnackBar(view, "账号不能为空");
    }

    @Override
    public void setPasswordError() {
        Util.showSnackBar(view, "密码不能为空");
    }


    @Override
    public void switch2Person(PersonBean personBean) {
        MyApplication.setmPersonBean(LoginActivity.this, personBean);
        binder.refresh(personBean);
        Util.toAnotherActivity(LoginActivity.this, MainActivity.class);
        L.i("refresh");
        finish();
    }

    @Override
    public void switch2Register() {
        Util.toAnotherActivity(LoginActivity.this, RegisterActivity.class);
    }


}
