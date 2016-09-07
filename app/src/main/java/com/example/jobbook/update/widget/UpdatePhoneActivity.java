package com.example.jobbook.update.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.person.widget.UserDetailActivity;
import com.example.jobbook.update.presenter.UpdatePhonePresenter;
import com.example.jobbook.update.presenter.UpdatePhonePresenterImpl;
import com.example.jobbook.update.view.UpdatePhoneView;
import com.example.jobbook.util.Util;
import com.jude.smssdk_mob.SMSManager;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePhoneActivity extends Activity implements View.OnClickListener, UpdatePhoneView {

    private ImageButton mBackImageButton;
    private TextView mOriginalPhoneTextView;
    private EditText mCodeEditText;
    private Button mGetCodeButton;
    private EditText mNewPhoneEditText;
    private TextView mCompleteTextView;
    private PersonBean personBean = MyApplication.getmPersonBean();
    private UpdatePhonePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_change_phone);
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
        mPresenter =  new UpdatePhonePresenterImpl(this);
        mBackImageButton.setOnClickListener(this);
        mGetCodeButton.setOnClickListener(this);
        mCompleteTextView.setOnClickListener(this);
        mOriginalPhoneTextView.setText(personBean.getTelephone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_change_phone_back_ib:
                finish();
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
        Util.toAnotherActivity(this, UserDetailActivity.class);
        this.finish();
    }

    @Override
    public void getCode() {
        SMSManager.getInstance().sendMessage(this, "86", mOriginalPhoneTextView.getText().toString());
    }

    @Override
    public void complete(Context mContext) {
        mPresenter.complete(mContext, MyApplication.getmPersonBean().getAccount(), mNewPhoneEditText.getText().toString(), mCodeEditText.getText().toString());
    }

    @Override
    public void codeBlankError() {
        showSnackbar("验证码为空！");
    }

    @Override
    public void codeError() {
        showSnackbar("手机验证码错误");
    }

    @Override
    public void newPhoneBlankError() {
        showSnackbar("新手机号码为空");
    }

    @Override
    public void success() {
        showSnackbar("保存成功！");
    }

    @Override
    public void networkError() {
        showSnackbar("网络错误！");
    }

    private void showSnackbar(String content) {
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
}
