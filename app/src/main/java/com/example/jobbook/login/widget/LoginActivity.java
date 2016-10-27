package com.example.jobbook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.login.presenter.LoginPresenter;
import com.example.jobbook.login.presenter.LoginPresenterImpl;
import com.example.jobbook.login.view.LoginView;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.register.widget.RegisterActivity;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/6/2.
 */
public class LoginActivity extends Activity implements View.OnClickListener, LoginView {

    private Button mLoginButton;
    private TextView mRegisterTextView;
    private ImageButton mCloseImageButton;
    private LoginPresenter presenter;
    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private LinearLayout mLoadingLinearLayout;
    private TextView mForgetPwdTextView;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        view = getWindow().getDecorView();
        initView();
        initEvents();
        presenter = new LoginPresenterImpl(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
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

    private void initView() {
        mLoginButton = (Button) findViewById(R.id.login_login_bt);
        mLoginButton.setOnClickListener(this);
        mRegisterTextView = (TextView) findViewById(R.id.login_register_tv);
        mCloseImageButton = (ImageButton) findViewById(R.id.login_close_ib);
        mCloseImageButton.setOnClickListener(this);
        mAccountEditText = (EditText) findViewById(R.id.login_account_et);
        mPasswordEditText = (EditText) findViewById(R.id.login_password_et);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
        mForgetPwdTextView = (TextView) findViewById(R.id.login_forget_tv);
        mRegisterTextView.setOnClickListener(this);
        mForgetPwdTextView.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_bt:
                presenter.loginCheck(mAccountEditText.getText().toString(), mPasswordEditText.getText().toString());
                break;

            case R.id.login_register_tv:
                switch2Register();
                break;

            case R.id.login_close_ib:
                Bundle bundle = new Bundle();
                bundle.putString("CHANGE_TO", "JOB");
                Util.toAnotherActivity(LoginActivity.this, MainActivity.class, bundle);
                finish();
                break;
            case R.id.login_forget_tv:
                Util.toAnotherActivity(LoginActivity.this, ForgetPwdFirstActivity.class);
                finish();
                break;
        }
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
        Util.toAnotherActivity(LoginActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void switch2Register() {
        Util.toAnotherActivity(LoginActivity.this, RegisterActivity.class);
    }

}
