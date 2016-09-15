package com.example.jobbook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.login.presenter.ForgetPwdFirstPresenter;
import com.example.jobbook.login.presenter.ForgetPwdFirstPresenterImpl;
import com.example.jobbook.login.view.ForgetPwdFirstView;
import com.example.jobbook.main.widget.ContainerFragment;
import com.example.jobbook.util.Util;
import com.jude.smssdk_mob.SMSManager;
import com.jude.smssdk_mob.TimeListener;

import cn.smssdk.SMSSDK;
import cn.smssdk.SMSSDK.VerifyCodeReadListener;

/**
 * Created by 椰树 on 2016/9/14.
 */
public class ForgetPwdFirstActivity extends Activity implements ForgetPwdFirstView, View.OnClickListener, TimeListener {
    private ImageButton mBackImageButton;
    private TextView mNextTextView;
    private EditText mPhoneEditText;
    private EditText mCodeEditText;
    private Button mGetCodeButton;
    private LinearLayout mLoadingLayout;
    private ForgetPwdFirstPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_first);
        initViews();
        initEvents();
    }
    private void initViews(){
        mBackImageButton = (ImageButton) findViewById(R.id.forget_pwd_first_back_ib);
        mNextTextView = (TextView) findViewById(R.id.forget_pwd_first_next_tv);
        mPhoneEditText = (EditText) findViewById(R.id.forget_pwd_first_phone_et);
        mCodeEditText = (EditText) findViewById(R.id.forget_pwd_first_code_et);
        mGetCodeButton = (Button) findViewById(R.id.forget_pwd_first_code_bt);
        mLoadingLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }
    private void initEvents(){
        hideProgress();
        mPresenter = new ForgetPwdFirstPresenterImpl(this);
        SMSManager.getInstance().registerTimeListener(this);
        mNextTextView.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);
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
    public void close() {
        finish();
    }

    @Override
    public void phoneBlankError() {
        showSnackBar("手机号码不能为空！");
    }

    @Override
    public void codeBlankError() {
        showSnackBar("验证码不能为空！");
    }

    @Override
    public void checkSuccess() {
        SMSManager.getInstance().sendMessage(this, "86", mPhoneEditText.getText().toString());
    }

    @Override
    public void checkFailure(int error) {
        switch (error){
            case 0:
                showSnackBar("网络错误！");
                break;
            case 1:
                showSnackBar("该手机号未注册！");
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
        showSnackBar("验证码错误!");
    }


    @Override
    public void checkAccount(){
        mPresenter.checkAccount(mPhoneEditText.getText().toString());
    }

    @Override
    public void next(Context mContext) {
        mPresenter.next(mContext, mCodeEditText.getText().toString(), mPhoneEditText.getText().toString());
    }

    private void showSnackBar(String content){
        View view = getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_pwd_first_code_bt:
                checkAccount();
                break;
            case R.id.forget_pwd_first_next_tv:
                next(this);
                break;
        }
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if(lastSecond > 0){
            mGetCodeButton.setText(lastSecond + "");
        }else{
            mGetCodeButton.setText("获取验证码");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        mGetCodeButton.setClickable(valuable);
    }

    @Override
    public void onDestroy(){
        SMSManager.getInstance().unRegisterTimeListener(this);
        super.onDestroy();
    }
}
