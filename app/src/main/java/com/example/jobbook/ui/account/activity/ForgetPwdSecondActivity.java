package com.example.jobbook.ui.account.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.base.contract.account.ForgetPwdContract;
import com.example.jobbook.presenter.account.ForgetPwdSecondPresenter;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdSecondActivity extends Activity implements ForgetPwdContract.ForgetPwdSecondView {

    @BindView(R.id.forget_pwd_second_finish_tv)
    TextView mFinishTextView;

    @BindView(R.id.forget_pwd_second_new_pwd_et)
    EditText mNewPwdEditText;

    @BindView(R.id.forget_pwd_second_confirm_pwd_et)
    EditText mConfirmEditText;

    @BindView(R.id.activity_forget_pwd_second_loading)
    ViewStub mLoadingLayout;

    private String account;
    private ForgetPwdSecondPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_second);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        Bundle bundle = this.getIntent().getExtras();
        account = bundle.getString("PHONE");
        mPresenter = new ForgetPwdSecondPresenter(this);
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
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    @Override
    public void phoneBlankError() {
        SnackBarUtil.showSnackBar(this, "密码不能为空！");
    }

    @Override
    public void confirmPhoneBlankError() {
        SnackBarUtil.showSnackBar(this, "确认密码不能为空！");
    }

    @Override
    public void differentError() {
        SnackBarUtil.showSnackBar(this, "两次密码不一致！");
    }

    @Override
    public void success() {
        SnackBarUtil.showSnackBar(this, "保存成功！");
        Util.toAnotherActivity(ForgetPwdSecondActivity.this, LoginActivity.class);
        finish();
    }

    @OnClick(R.id.forget_pwd_second_finish_tv)
    public void click_finish() {
        mPresenter.complete(account, mNewPwdEditText.getText().toString(), mConfirmEditText.getText().toString());
    }

    @OnClick(R.id.activity_forget_pwd_second_layout)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
