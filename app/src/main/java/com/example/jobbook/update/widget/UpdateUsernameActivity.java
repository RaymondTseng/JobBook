package com.example.jobbook.update.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.person.widget.UserDetailActivity;
import com.example.jobbook.update.presenter.UpdateUsernamePresenter;
import com.example.jobbook.update.presenter.UpdateUsernamePresenterImpl;
import com.example.jobbook.update.view.UpdateUsernameView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateUsernameActivity extends Activity implements View.OnClickListener, UpdateUsernameView {

    private ImageButton mBackImageButton;
    private EditText mUserNameEditText;
    private TextView mCompleteTextView;
    private UpdateUsernamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_change_username);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.person_change_username_back_ib);
        mUserNameEditText = (EditText) findViewById(R.id.person_change_username_et);
        mCompleteTextView = (TextView) findViewById(R.id.person_change_username_complete_tv);
    }


    private void initEvents() {
        mBackImageButton.setOnClickListener(this);
        mCompleteTextView.setOnClickListener(this);
        presenter = new UpdateUsernamePresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_change_username_back_ib:
                close();
                break;
            case R.id.person_change_username_complete_tv:
                complete();
                break;
        }
    }

    @Override
    public void close() {
        Util.toAnotherActivity(this, UserDetailActivity.class);
        finish();
    }

    @Override
    public void complete() {
        presenter.update(MyApplication.getmPersonBean().getAccount(), mUserNameEditText.getText().toString());
    }

    @Override
    public void usernameBlankError() {
        showSnackbar("新昵称为空");
    }

    @Override
    public void success() {
        showSnackbar("修改昵称成功");
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
