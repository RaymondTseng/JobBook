package com.example.jobbook.ui.account.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.base.contract.account.ForgetPwdContract;
import com.example.jobbook.presenter.account.ForgetPwdFirstPresenter;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdFirstActivity extends Activity implements ForgetPwdContract.ForgetPwdFirstView, SMSSDKManager.TimeListener {

    @BindView(R.id.activity_forget_pwd_first_layout)
    LinearLayout mParentLayout;

    @BindView(R.id.forget_pwd_first_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.forget_pwd_first_next_tv)
    TextView mNextTextView;

    @BindView(R.id.forget_pwd_first_phone_et)
    EditText mPhoneEditText;

    @BindView(R.id.forget_pwd_first_code_et)
    EditText mCodeEditText;

    @BindView(R.id.forget_pwd_first_code_bt)
    Button mGetCodeButton;

    @BindView(R.id.activity_forget_pwd_first_loading)
    ViewStub mLoadingLayout;
    private ForgetPwdFirstPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_first);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        mPresenter = new ForgetPwdFirstPresenter(this);
        SMSSDKManager.getInstance().registerTimeListener(this);
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
        SnackBarUtil.showSnackBar(this, "手机号码不能为空！");
    }

    @Override
    public void codeBlankError() {
        SnackBarUtil.showSnackBar(this, "验证码不能为空！");
    }

    @Override
    public void checkSuccess() {
        SMSSDKManager.getInstance().sendMessage(this, "86", mPhoneEditText.getText().toString());
    }

    @Override
    public void codeSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("PHONE", mPhoneEditText.getText().toString());
        Util.toAnotherActivity(ForgetPwdFirstActivity.this, ForgetPwdSecondActivity.class, bundle);
    }

    @Override
    public void codeFailure() {
        SnackBarUtil.showSnackBar(this, "验证码错误!");
    }

    @OnClick(R.id.forget_pwd_first_code_bt)
    public void click_code() {
        mPresenter.checkAccount(mPhoneEditText.getText().toString());
    }

    @OnClick(R.id.forget_pwd_first_next_tv)
    public void next() {
        mPresenter.next(this, mCodeEditText.getText().toString(), mPhoneEditText.getText().toString());
    }

    @OnClick(R.id.forget_pwd_first_back_ib)
    public void back() {
        Util.toAnotherActivity(ForgetPwdFirstActivity.this, LoginActivity.class);
        finish();
    }

    @OnClick(R.id.activity_forget_pwd_first_layout)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0) {
            mGetCodeButton.setText(lastSecond + "");
        } else {
            mGetCodeButton.setText("获取验证码");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        mGetCodeButton.setClickable(valuable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDKManager.getInstance().unRegisterTimeListener(this);
    }
}
