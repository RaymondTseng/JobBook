package com.example.jobbook.ui.person.activity;

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
import com.example.jobbook.base.contract.person.ForgetPwdContract;
import com.example.jobbook.presenter.person.ForgetPwdFirstPresenter;
import com.example.jobbook.util.SMSSDKManager;
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
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_first);
        ButterKnife.bind(this);
        view = getWindow().getDecorView();
        initEvents();
    }

    private void initEvents() {
        hideProgress();
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

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void phoneBlankError() {
        Util.showSnackBar(view, "手机号码不能为空！");
    }

    @Override
    public void codeBlankError() {
        Util.showSnackBar(view, "验证码不能为空！");
    }

    @Override
    public void checkSuccess() {
        SMSSDKManager.getInstance().sendMessage(this, "86", mPhoneEditText.getText().toString());
    }

    @Override
    public void checkFailure(int error) {
        switch (error) {
            case 0:
                Util.showSnackBar(view, "网络错误！");
                break;
            case 1:
                Util.showSnackBar(view, "该手机号未注册！");
                break;
        }
    }

    @Override
    public void codeSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("PHONE", mPhoneEditText.getText().toString());
        Util.toAnotherActivity(ForgetPwdFirstActivity.this, ForgetPwdSecondActivity.class, bundle);
    }

    @Override
    public void codeFailure() {
        Util.showSnackBar(view, "验证码错误!");
    }


    @Override
    public void checkAccount() {
        mPresenter.checkAccount(mPhoneEditText.getText().toString());
    }

    @Override
    public void next(Context mContext) {
        mPresenter.next(mContext, mCodeEditText.getText().toString(), mPhoneEditText.getText().toString());
    }

    @OnClick(R.id.forget_pwd_first_code_bt)
    public void click_code() {
        checkAccount();
    }

    @OnClick(R.id.forget_pwd_first_next_tv)
    public void next() {
        next(this);
    }

    @OnClick(R.id.forget_pwd_first_back_ib)
    public void back() {
        Util.toAnotherActivity(ForgetPwdFirstActivity.this, LoginActivity.class);
        close();
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
        SMSSDKManager.getInstance().unRegisterTimeListener(this);
        super.onDestroy();
    }
}
