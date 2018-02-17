package com.example.jobbook.ui.account.activity;

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

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.account.RegisterContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.presenter.account.RegisterPresenter;
import com.example.jobbook.ui.main.activity.MainActivity;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterActivity extends Activity implements RegisterContract.View, SMSSDKManager.TimeListener {

    @BindView(R.id.register_register_bt)
    Button mRegisterButton;

    @BindView(R.id.register_account_et)
    EditText mAccountEditText;

    @BindView(R.id.register_username_et)
    EditText mUserNameEditText;

    @BindView(R.id.register_password_et)
    EditText mPwdEditText;

    @BindView(R.id.register_confirm_password_et)
    EditText mPwdAgainEditText;

    @BindView(R.id.register_code_et)
    EditText mCodeEditText;

    @BindView(R.id.register_code_bt)
    Button mGetCodeButton;

    @BindView(R.id.register_close_ib)
    ImageButton mCloseImageButton;

    @BindView(R.id.activity_register_loading)
    ViewStub mLoadingLinearLayout;

    @BindView(R.id.activity_register_layout)
    LinearLayout mParentLayout;

    private View view;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        view = getWindow().getDecorView();
        initEvents();
    }

    private void initEvents() {
        mLoadingLinearLayout.inflate();
        presenter = new RegisterPresenter(this);
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
    public void showLoadFailMsg(String msg) {

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

    @OnClick(R.id.register_register_bt)
    public void register() {
        presenter.registerCheck(RegisterActivity.this, mAccountEditText.getText().toString(),
                mUserNameEditText.getText().toString(), mPwdEditText.getText().toString(), mPwdAgainEditText.getText().toString(),
                mCodeEditText.getText().toString());
    }

    @OnClick(R.id.register_close_ib)
    public void register_close() {
        switch2Login();
    }

    @OnClick(R.id.register_code_bt)
    public void get_code() {
        if (!TextUtils.isEmpty(mAccountEditText.getText().toString())) {
            SMSSDKManager.getInstance().sendMessage(RegisterActivity.this, "86", mAccountEditText.getText().toString());
        } else {
            codeBlankError();
        }
    }

    @OnClick(R.id.activity_register_layout)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
