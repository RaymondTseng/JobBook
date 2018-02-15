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
import com.example.jobbook.presenter.person.UpdateUsernamePresenter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateUsernameActivity extends Activity implements UpdateContract.UpdateUsernameView {

    @BindView(R.id.person_change_username_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.person_change_username_et)
    EditText mUserNameEditText;

    @BindView(R.id.person_change_username_complete_tv)
    TextView mCompleteTextView;

    @BindView(R.id.activity_person_change_username_loading)
    ViewStub mLoadingLinearLayout;

    private UpdateUsernamePresenter presenter;
    private MyApplication mMyApplication;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_username);
        ButterKnife.bind(this);
        view = getWindow().getDecorView();
        initEvents();
    }

    private void initEvents() {
        mLoadingLinearLayout.inflate();
        presenter = new UpdateUsernamePresenter(this);
        mMyApplication = (MyApplication) getApplication();
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.person_change_username_back_ib)
    public void back() {
        close();
    }

    @OnClick(R.id.person_change_username_complete_tv)
    public void change_complete() {
        complete();
    }

    @Override
    public void close() {
        mMyApplication.getHandler().sendEmptyMessage(1);
        L.i(MyApplication.getmPersonBean().getUsername());
        finish();
    }

    @Override
    public void complete() {
        presenter.update(this, MyApplication.getAccount(), mUserNameEditText.getText().toString());
    }

    @Override
    public void usernameBlankError() {
        Util.showSnackBar(view, "新昵称为空");
    }

    @Override
    public void success() {
        Util.showSnackBar(view, "修改昵称成功");
    }

    @Override
    public void networkError() {
        Util.showSnackBar(view, "网络错误！");
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

    }

}
