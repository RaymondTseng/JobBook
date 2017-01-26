package com.example.jobbook.update.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.update.presenter.UpdatePhonePresenter;
import com.example.jobbook.update.presenter.UpdatePhonePresenterImpl;
import com.example.jobbook.update.view.UpdatePhoneView;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.Util;


/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhoneActivity extends Activity implements View.OnClickListener, UpdatePhoneView, SMSSDKManager.TimeListener {

    private ImageButton mBackImageButton;
    private TextView mOriginalPhoneTextView;
    private EditText mCodeEditText;
    private Button mGetCodeButton;
    private EditText mNewPhoneEditText;
    private TextView mCompleteTextView;
    private PersonBean personBean;
    private UpdatePhonePresenter mPresenter;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_phone);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.person_change_phone_back_ib);
        mOriginalPhoneTextView = (TextView) findViewById(R.id.person_change_phone_original_phone_tv);
        mCodeEditText = (EditText) findViewById(R.id.person_change_phone_code_et);
        mGetCodeButton = (Button) findViewById(R.id.person_change_phone_code_bt);
        mNewPhoneEditText = (EditText) findViewById(R.id.person_change_phone_new_phone_et);
        mCompleteTextView = (TextView) findViewById(R.id.person_change_phone_complete_tv);
    }

    private void initEvents() {
        mPresenter = new UpdatePhonePresenterImpl(this);
        mBackImageButton.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);
        mCompleteTextView.setOnClickListener(this);
        if(MyApplication.getmLoginStatus() != 0){
            personBean = MyApplication.getmPersonBean();
            L.i("phone", "result:" + personBean.getAccount());
            mOriginalPhoneTextView.setText(personBean.getAccount());
            SMSSDKManager.getInstance().setDefaultDelay(60);
            SMSSDKManager.getInstance().registerTimeListener(this);
        }else{
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_change_phone_back_ib:
                close();
                break;

            case R.id.person_change_phone_code_bt:
                getCode();
                break;
            case R.id.person_change_phone_complete_tv:
                complete(this);
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void getCode() {
        SMSSDKManager.getInstance().sendMessage(this, "86", mOriginalPhoneTextView.getText().toString());
    }

    @Override
    public void complete(Context mContext) {
        mPresenter.complete(mContext, MyApplication.getAccount(), mNewPhoneEditText.getText().toString(), mCodeEditText.getText().toString());
    }

    @Override
    public void codeBlankError() {
        Util.showSnackBar(view, "验证码为空！");
    }

    @Override
    public void codeError() {
        Util.showSnackBar(view, "手机验证码错误");
    }

    @Override
    public void newPhoneBlankError() {
        Util.showSnackBar(view, "新手机号码为空");
    }

    @Override
    public void success() {
        Util.showSnackBar(view, "保存成功！");
    }

    @Override
    public void networkError() {
        Util.showSnackBar(view, "网络错误！");
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

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
