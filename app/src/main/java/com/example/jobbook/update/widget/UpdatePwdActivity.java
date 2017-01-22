package com.example.jobbook.update.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.person.widget.OldUserDetailActivity;
import com.example.jobbook.update.presenter.UpdatePwdPresenter;
import com.example.jobbook.update.presenter.UpdatePwdPresenterImpl;
import com.example.jobbook.update.view.UpdatePwdView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePwdActivity extends Activity implements View.OnClickListener, UpdatePwdView {

    private ImageButton mBackImageButton;
    private EditText mOriginalPwdEditText;
    private EditText mNewPwdEditText;
    private EditText mNewPwdConfirmEditText;
    private TextView mCompleteTextView;
    private UpdatePwdPresenter presenter;
    private LinearLayout mLoadingLinearLayout;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_pwd);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.person_change_pwd_back_ib);
        mOriginalPwdEditText = (EditText) findViewById(R.id.person_change_pwd_original_pwd_et);
        mNewPwdEditText = (EditText) findViewById(R.id.person_change_pwd_new_pwd_et);
        mNewPwdConfirmEditText = (EditText) findViewById(R.id.person_change_pwd_new_pwd_confirm_et);
        mCompleteTextView = (TextView) findViewById(R.id.person_change_pwd_complete_tv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        mBackImageButton.setOnClickListener(this);
        mCompleteTextView.setOnClickListener(this);
        presenter = new UpdatePwdPresenterImpl(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_change_pwd_back_ib:
                close();
                break;
            case R.id.person_change_pwd_complete_tv:
                complete();
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void complete() {
        presenter.updatePwd(this, MyApplication.getAccount(), mOriginalPwdEditText.getText().toString(),
                mNewPwdEditText.getText().toString(), mNewPwdConfirmEditText.getText().toString());
    }

    @Override
    public void oPwdBlankError() {
        Util.showSnackBar(view, "原密码为空");
    }

    @Override
    public void nPwdBlankError() {
        Util.showSnackBar(view, "新密码为空");
    }

    @Override
    public void nPwdConfirmBlankError() {
        Util.showSnackBar(view, "确认密码为空");
    }

    @Override
    public void pwdConfirmError() {
        Util.showSnackBar(view, "新密码与确认密码不一致");
    }

    @Override
    public void success() {
        Util.showSnackBar(view, "修改密码成功");
    }

    @Override
    public void networkError() {
        Util.showSnackBar(view, "网络错误！");
    }

    @Override
    public void oPwdError() {
        Util.showSnackBar(view, "原密码错误！");
    }

    @Override
    public void oPwdEqualnPwdError() {
        Util.showSnackBar(view, "原密码与新密码一致！");
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

}
