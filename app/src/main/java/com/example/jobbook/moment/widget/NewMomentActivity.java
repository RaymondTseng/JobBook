package com.example.jobbook.moment.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.moment.presenter.NewMomentPresenter;
import com.example.jobbook.moment.presenter.NewMomentPresenterImpl;
import com.example.jobbook.moment.view.NewMomentView;
import com.example.jobbook.util.AffectUtil;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewMomentActivity extends Activity implements NewMomentView, View.OnClickListener, TextWatcher {

    private static int NO_CHANGE_COLOR = 0;
    private static int CHANGE_COLOR = 1;

    private ImageButton mCloseImageButton;
//    private EditText mNewQuestionTitleEditText;
    private EditText mNewMomentContentEditText;
    private TextView mNewMomentReleaseTextView;
    private NewMomentPresenter mNewMomentPresenter;
    private LinearLayout mLoadingLinearLayout;

    private MyApplication myApplication;
    private View view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_COLOR) {
                mNewMomentReleaseTextView.setClickable(true);
                mNewMomentReleaseTextView.setTextColor(Color.WHITE);
            } else if (msg.what == NO_CHANGE_COLOR) {
                mNewMomentReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_moment);
        mNewMomentPresenter = new NewMomentPresenterImpl(this);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mCloseImageButton = (ImageButton) findViewById(R.id.activity_publish_moment_close_ib);
//        mNewMomentTitleEditText = (EditText) findViewById(R.id.activity_publish_question_title_et);
        mNewMomentContentEditText = (EditText) findViewById(R.id.activity_publish_moment_content_et);
        mNewMomentReleaseTextView = (TextView) findViewById(R.id.activity_publish_moment_release_tv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        mCloseImageButton.setOnClickListener(this);
//        mNewMomentTitleEditText.addTextChangedListener(this);
        mNewMomentContentEditText.addTextChangedListener(this);
        mNewMomentReleaseTextView.setOnClickListener(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
        mNewMomentReleaseTextView.setTextColor(Color.WHITE);
//        mNewQuestionTitleEditText.setOnFocusChangeListener(AffectUtil.changeHintColorListener(mNewMomentTitleEditText));
        mNewMomentContentEditText.setOnFocusChangeListener(AffectUtil.changeHintColorListener(mNewMomentContentEditText));
    }

    @Override
    public void showError() {
        Util.showSnackBar(view, "发表失败,请重试！");
    }

    @Override
    public void showSuccess() {
        Util.showSnackBar(view, "发表成功!");
    }

    @Override
    public void publishNoLoginError() {
        Util.showSnackBar(view, "请先登录！");
    }


    @Override
    public void close() {
        myApplication = (MyApplication) getApplication();
        Handler handler = myApplication.getHandler();
        handler.sendEmptyMessage(1);
        this.finish();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_publish_moment_close_ib:
                close();
                break;
            case R.id.activity_publish_moment_release_tv:
                MomentBean momentBean = new MomentBean();
                if (MyApplication.getmLoginStatus() == 0) {
                    publishNoLoginError();
                } else {
                    mNewMomentReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
                    momentBean.setAuthor(MyApplication.getmPersonBean());
//                    momentBean.setTitle(mNewMomentTitleEditText.getText().toString());
                    momentBean.setContent(mNewMomentContentEditText.getText().toString());
                    mNewMomentPresenter.newmoment(momentBean);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        changeColor();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void changeColor() {
        if (!TextUtils.isEmpty(mNewMomentContentEditText.getText())) {
            Message message = new Message();
            message.what = CHANGE_COLOR;
            handler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = NO_CHANGE_COLOR;
            handler.sendMessage(message);
        }
    }
}
