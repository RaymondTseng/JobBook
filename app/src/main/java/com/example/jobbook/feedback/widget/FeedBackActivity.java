package com.example.jobbook.feedback.widget;

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

import com.example.jobbook.R;
import com.example.jobbook.feedback.presenter.FeedBackPresenter;
import com.example.jobbook.feedback.presenter.FeedBackPresenterImpl;
import com.example.jobbook.feedback.view.FeedBackView;
import com.example.jobbook.util.AffectUtil;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackActivity extends Activity implements FeedBackView, View.OnClickListener, TextWatcher {

    private static int NO_CHANGE_COLOR = 0;
    private static int CHANGE_COLOR = 1;

    private ImageButton mBackImageButton;
    private TextView mFeedBackTextView;
    private EditText mFeedBackMailEditText;
    private EditText mFeedBackContentEditText;
    private FeedBackPresenter mFeedBackPresenter;
    private LinearLayout mLoadingLinearLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_COLOR) {
                mFeedBackTextView.setTextColor(Color.WHITE);
            } else if (msg.what == NO_CHANGE_COLOR) {
                mFeedBackTextView.setTextColor(Color.parseColor("#61ffffff"));
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_feedback);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.suggestion_feedback_back_ib);
        mFeedBackTextView = (TextView) findViewById(R.id.suggestion_feedback_new_tv);
        mFeedBackMailEditText = (EditText) findViewById(R.id.suggestion_feedback_mail_et);
        mFeedBackContentEditText = (EditText) findViewById(R.id.suggestion_feedback_content_et);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
        mLoadingLinearLayout.setVisibility(View.GONE);
        mFeedBackTextView.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
    }

    private void initEvents() {
        mFeedBackTextView.setTextColor(Color.WHITE);
        mFeedBackPresenter = new FeedBackPresenterImpl(this);
        mFeedBackTextView.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mFeedBackMailEditText.addTextChangedListener(this);
        mFeedBackContentEditText.addTextChangedListener(this);
        mFeedBackContentEditText.setOnFocusChangeListener(AffectUtil.changeHintColorListener(mFeedBackContentEditText));
    }

    @Override
    public void showError() {
        Util.showSnackBar(getWindow().getDecorView(), "提交失败!");
    }

    @Override
    public void emailError() {
        Util.showSnackBar(getWindow().getDecorView(), "邮箱格式错误！");
    }

    @Override
    public void showSuccess() {
        Util.showSnackBar(getWindow().getDecorView(), "提交成功！");
    }

    @Override
    public void back() {
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
            case R.id.suggestion_feedback_back_ib:
                back();
                break;
            case R.id.suggestion_feedback_new_tv:
                mFeedBackTextView.setTextColor(Color.parseColor("#61ffffff"));
                mFeedBackPresenter.feedback(mFeedBackMailEditText.getText().toString(), mFeedBackContentEditText.getText().toString());
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
        if (!TextUtils.isEmpty(mFeedBackMailEditText.getText()) && !TextUtils.isEmpty(mFeedBackContentEditText.getText())) {
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
