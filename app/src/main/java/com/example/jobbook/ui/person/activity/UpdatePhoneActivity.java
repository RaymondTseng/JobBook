package com.example.jobbook.ui.person.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.UpdateContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.presenter.person.UpdatePhonePresenter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.SnackBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhoneActivity extends Activity implements UpdateContract.UpdatePhoneView, SMSSDKManager.TimeListener {

    @BindView(R.id.person_change_phone_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.person_change_phone_original_phone_tv)
    TextView mOriginalPhoneTextView;

    @BindView(R.id.person_change_phone_code_et)
    EditText mCodeEditText;

    @BindView(R.id.person_change_phone_code_bt)
    Button mGetCodeButton;

    @BindView(R.id.person_change_phone_new_phone_et)
    EditText mNewPhoneEditText;

    @BindView(R.id.person_change_phone_complete_tv)
    TextView mCompleteTextView;

    @BindView(R.id.activity_person_change_phone_loading)
    ViewStub mLoadingLinearLayout;

    private PersonBean personBean;
    private UpdatePhonePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_phone);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        mPresenter = new UpdatePhonePresenter(this);
        if (MyApplication.getmLoginStatus() != 0) {
            personBean = MyApplication.getmPersonBean();
            L.i("result:" + personBean.getAccount());
            mOriginalPhoneTextView.setText(personBean.getAccount());
            SMSSDKManager.getInstance().setDefaultDelay(60);
            SMSSDKManager.getInstance().registerTimeListener(this);
        } else {
            finish();
        }

    }

    @OnClick(R.id.person_change_phone_back_ib)
    public void back() {
        finish();
    }

    @OnClick(R.id.person_change_phone_code_bt)
    public void get_code() {
        getCode();
    }

    @OnClick(R.id.person_change_phone_complete_tv)
    public void complete() {
        mPresenter.complete(this, MyApplication.getAccount(), mNewPhoneEditText.getText().toString(), mCodeEditText.getText().toString());
    }

    @Override
    public void getCode() {
        SMSSDKManager.getInstance().sendMessage(this, "86", mOriginalPhoneTextView.getText().toString());
    }

    @Override
    public void codeBlankError() {
        SnackBarUtil.showSnackBar(this, "验证码为空！");
    }

    @Override
    public void codeError() {
        SnackBarUtil.showSnackBar(this, "手机验证码错误");
    }

    @Override
    public void newPhoneBlankError() {
        SnackBarUtil.showSnackBar(this, "新手机号码为空");
    }

    @Override
    public void success() {
        SnackBarUtil.showSnackBar(this, "保存成功！");
        finish();
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        SMSSDKManager.getInstance().unRegisterTimeListener(this);
        super.onDestroy();
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0) {
            mGetCodeButton.setText(lastSecond);
        } else {
            mGetCodeButton.setText("发送验证码");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        mGetCodeButton.setEnabled(valuable);
    }
}
