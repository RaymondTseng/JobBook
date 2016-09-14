package com.example.jobbook.login.widget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import com.example.jobbook.util.L;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        initView();
        presenter = new LoginPresenterImpl(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
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
        mRegisterTextView.setOnClickListener(this);
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
                Util.toAnotherActivity(LoginActivity.this, MainActivity.class);
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
        L.i("loginfragment", "error");
    }

    @Override
    public void setUserError() {
        showSnackbar("账号或密码错误");
    }

    @Override
    public void setAccountError() {
        showSnackbar("账号不能为空");
    }

    @Override
    public void setPasswordError() {
        showSnackbar("密码不能为空");
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

    private void showSnackbar(String content) {
        View view = this.findViewById(R.id.main_layout);
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
