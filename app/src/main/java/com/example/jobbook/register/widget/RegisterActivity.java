package com.example.jobbook.register.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.app.constants.Urls;
import com.example.jobbook.login.widget.LoginActivity;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.register.presenter.RegisterPresenter;
import com.example.jobbook.register.presenter.RegisterPresenterImpl;
import com.example.jobbook.register.view.RegisterView;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;



/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterActivity extends Activity implements RegisterView, View.OnClickListener, SMSSDKManager.TimeListener {

    private Button mRegisterButton;
    private EditText mAccountEditText;
    private EditText mUserNameEditText;
    private EditText mPwdEditText;
    private EditText mPwdAgainEditText;
    private EditText mCodeEditText;
    private Button mGetCodeButton;
    private ImageButton mCloseImageButton;
    private ViewStub mLoadingLinearLayout;
    private RegisterPresenter presenter;
    private LinearLayout mParentLayout;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mParentLayout = (LinearLayout) findViewById(R.id.activity_register_layout);
        mRegisterButton = (Button) findViewById(R.id.register_register_bt);
        mAccountEditText = (EditText) findViewById(R.id.register_account_et);
        mUserNameEditText = (EditText) findViewById(R.id.register_username_et);
        mPwdEditText = (EditText) findViewById(R.id.register_password_et);
        mPwdAgainEditText = (EditText) findViewById(R.id.register_confirm_password_et);
        mCodeEditText = (EditText) findViewById(R.id.register_code_et);
        mCloseImageButton = (ImageButton) findViewById(R.id.register_close_ib);
        mGetCodeButton = (Button) findViewById(R.id.register_code_bt);
        mLoadingLinearLayout = (ViewStub) findViewById(R.id.activity_register_loading);
        mLoadingLinearLayout.inflate();
    }

    private void initEvents() {
        presenter = new RegisterPresenterImpl(this);
        mRegisterButton.setOnClickListener(this);
        mCloseImageButton.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);
        mParentLayout.setOnClickListener(this);
        L.i("load code:" + Urls.GET_CODE_URL);
        SMSSDKManager.getInstance().registerTimeListener(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        // TODO Auto-generated method stub
//        super.onAttach(activity);
//        try {
////            mIRegisterChanged = (IRegisterChanged) activity;
//            mIRegisterChanged = (IRegisterChanged) getParentFragment();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + "must implement OnGridViewSelectedListener");
//        }
//    }

    @Override
    public void success() {
        Util.showSnackBar(view, "连接成功");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void networkError() {
        L.i("error");
    }

    @Override
    public void accountBlankError() {
        Util.showSnackBar(view, "手机号为空");
    }

    @Override
    public void pwdBlankError() {
        Util.showSnackBar(view, "密码为空");
    }

    @Override
    public void pwdConfirmBlankError() {
        Util.showSnackBar(view, "确认密码为空");
    }

    @Override
    public void pwdNotEqualError() {
        Util.showSnackBar(view, "密码与确认密码不一致");
    }

    @Override
    public void accountExistError() {
        Util.showSnackBar(view, "手机号已存在");
    }

    @Override
    public void switch2Person(PersonBean personBean) {
        MyApplication.setmPersonBean(RegisterActivity.this, personBean);
        Util.toAnotherActivity(RegisterActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void switch2Login() {
        Util.toAnotherActivity(RegisterActivity.this, LoginActivity.class);
        finish();
    }

    @Override
    public void accountIllegalError() {
        Util.showSnackBar(view, "账号存在非法字符");
    }

    @Override
    public void userNameBlankError() {
        Util.showSnackBar(view, "昵称为空");
    }

    @Override
    public void telephoneBlankError() {
        Util.showSnackBar(view, "手机号码为空");
    }

    @Override
    public void codeBlankError() {
        Util.showSnackBar(view, "验证码为空");
    }

    @Override
    public void codeError() {
        Util.showSnackBar(view, "验证码错误");
//        refreshCode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register_bt:
                presenter.registerCheck(RegisterActivity.this, mAccountEditText.getText().toString(),
                        mUserNameEditText.getText().toString(), mPwdEditText.getText().toString(), mPwdAgainEditText.getText().toString(),
                        mCodeEditText.getText().toString());
                break;
            case R.id.register_close_ib:
                switch2Login();
                break;
            case R.id.register_code_bt:
                if (!TextUtils.isEmpty(mAccountEditText.getText().toString())) {
                    SMSSDKManager.getInstance().sendMessage(RegisterActivity.this, "86", mAccountEditText.getText().toString());
                } else {
                    codeBlankError();
                }
                break;
            case R.id.activity_register_layout:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        SMSSDKManager.getInstance().unRegisterTimeListener(this);
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0) {
            mGetCodeButton.setText(lastSecond + "s");
        } else {
            mGetCodeButton.setText("发送验证码");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        mGetCodeButton.setEnabled(valuable);
    }

}
