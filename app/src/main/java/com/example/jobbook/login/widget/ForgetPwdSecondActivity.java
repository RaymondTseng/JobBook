package com.example.jobbook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.login.presenter.ForgetPwdSecondPresenter;
import com.example.jobbook.login.presenter.ForgetPwdSecondPresenterImpl;
import com.example.jobbook.login.view.ForgetPwdSecondView;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdSecondActivity extends Activity implements ForgetPwdSecondView, OnClickListener {
    private TextView mFinishTextView;
    private EditText mNewPwdEditText;
    private EditText mConfirmEditText;
    private ViewStub mLoadingLayout;
    private String account;
    private ForgetPwdSecondPresenter mPresenter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_second);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
//        mParentLayout = (LinearLayout) findViewById(R.id.activity_forget_pwd_second_layout);
        mFinishTextView = (TextView) findViewById(R.id.forget_pwd_second_finish_tv);
        mNewPwdEditText = (EditText) findViewById(R.id.forget_pwd_second_new_pwd_et);
        mConfirmEditText = (EditText) findViewById(R.id.forget_pwd_second_confirm_pwd_et);
        mLoadingLayout = (ViewStub) findViewById(R.id.activity_forget_pwd_second_loading);
    }

    private void initEvents() {
        hideProgress();
        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("PHONE");
        mPresenter = new ForgetPwdSecondPresenterImpl(this);
        mFinishTextView.setOnClickListener(this);
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void phoneBlankError() {
        Util.showSnackBar(view, "密码不能为空！");
    }

    @Override
    public void confirmPhoneBlankError() {
        Util.showSnackBar(view, "确认密码不能为空！");
    }

    @Override
    public void differentError() {
        Util.showSnackBar(view, "两次密码不一致！");
    }

    @Override
    public void success() {
        Util.showSnackBar(view, "保存成功！");
        Util.toAnotherActivity(ForgetPwdSecondActivity.this, LoginActivity.class);
        finish();
    }

    @Override
    public void failure() {
        Util.showSnackBar(view, "保存失败！");
    }

    @Override
    public void complete() {
        mPresenter.complete(account, mNewPwdEditText.getText().toString(), mConfirmEditText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_pwd_second_finish_tv:
                complete();
                break;
            case R.id.activity_forget_pwd_second_layout:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
}
