package com.example.jobbook.ui.person.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.UpdateContract;
import com.example.jobbook.presenter.person.UpdatePwdPresenter;
import com.example.jobbook.ui.account.activity.LoginActivity;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdatePwdActivity extends Activity implements UpdateContract.UpdatePwdView {

    @BindView(R.id.person_change_pwd_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.person_change_pwd_original_pwd_et)
    EditText mOriginalPwdEditText;

    @BindView(R.id.person_change_pwd_new_pwd_et)
    EditText mNewPwdEditText;

    @BindView(R.id.person_change_pwd_new_pwd_confirm_et)
    EditText mNewPwdConfirmEditText;

    @BindView(R.id.person_change_pwd_complete_tv)
    TextView mCompleteTextView;

    @BindView(R.id.activity_person_change_pwd_loading)
    ViewStub mLoadingLinearLayout;

    private UpdatePwdPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_pwd);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        mLoadingLinearLayout.inflate();
        presenter = new UpdatePwdPresenter(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.person_change_pwd_back_ib)
    public void back() {
        finish();
    }

    @OnClick(R.id.person_change_pwd_complete_tv)
    public void change_complete() {
        presenter.updatePwd(this, MyApplication.getAccount(), mOriginalPwdEditText.getText().toString(),
                mNewPwdEditText.getText().toString(), mNewPwdConfirmEditText.getText().toString());
    }

    @Override
    public void oPwdBlankError() {
        SnackBarUtil.showSnackBar(this, "原密码为空");
    }

    @Override
    public void nPwdBlankError() {
        SnackBarUtil.showSnackBar(this, "新密码为空");
    }

    @Override
    public void nPwdConfirmBlankError() {
        SnackBarUtil.showSnackBar(this, "确认密码为空");
    }

    @Override
    public void pwdConfirmError() {
        SnackBarUtil.showSnackBar(this, "新密码与确认密码不一致");
    }

    @Override
    public void success() {
        SnackBarUtil.showSnackBar(this, "更新密码成功，请重新登录！");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Util.toAnotherActivity(this, LoginActivity.class);
        finish();
    }

    @Override
    public void oPwdError() {
        SnackBarUtil.showSnackBar(this, "原密码错误！");
    }

    @Override
    public void oPwdEqualnPwdError() {
        SnackBarUtil.showSnackBar(this, "原密码与新密码一致！");
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

}
