package com.example.jobbook.question.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.main.widget.MainActivity;
import com.example.jobbook.question.presenter.NewQuestionPresenter;
import com.example.jobbook.question.presenter.NewQuestionPresenterImpl;
import com.example.jobbook.question.view.NewQuestionView;
import com.example.jobbook.util.Util;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionActivity extends Activity implements NewQuestionView, View.OnClickListener, TextWatcher {

    private static int NO_CHANGE_COLOR = 0;
    private static int CHANGE_COLOR = 1;

    private ImageButton mCloseImageButton;
    private EditText mNewQuestionTitleEditText;
    private EditText mNewQuestionContentEditText;
    private TextView mNewQuestionReleaseTextView;
    private NewQuestionPresenter mNewQuestionPresenter;
    private LinearLayout mLoadingLinearLayout;

    private MyApplication myApplication;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_COLOR) {
                mNewQuestionReleaseTextView.setClickable(true);
                mNewQuestionReleaseTextView.setTextColor(Color.WHITE);
            } else if (msg.what == NO_CHANGE_COLOR) {
                mNewQuestionReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        mNewQuestionPresenter = new NewQuestionPresenterImpl(this);
        initViews();
        initEvents();
    }

    private void initViews() {
        mCloseImageButton = (ImageButton) findViewById(R.id.activity_publish_close_ib);
        mNewQuestionTitleEditText = (EditText) findViewById(R.id.activity_publish_question_title_et);
        mNewQuestionContentEditText = (EditText) findViewById(R.id.activity_publish_question_content_et);
        mNewQuestionReleaseTextView = (TextView) findViewById(R.id.activity_publish_question_release_tv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        mCloseImageButton.setOnClickListener(this);
        mNewQuestionTitleEditText.addTextChangedListener(this);
        mNewQuestionContentEditText.addTextChangedListener(this);
        mNewQuestionReleaseTextView.setOnClickListener(this);
        mLoadingLinearLayout.setVisibility(View.GONE);
        mNewQuestionReleaseTextView.setTextColor(Color.WHITE);
    }

    @Override
    public void showError() {
        View view = getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, "发表失败,请重试！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void showSuccess() {
        View view = getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, "发表成功!", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void publishNoLoginError() {
        View view = getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, "请先登录！", Snackbar.LENGTH_LONG);
        snackbar.setAction("dismiss", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
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
            case R.id.activity_publish_close_ib:
                close();
                break;
            case R.id.activity_publish_question_release_tv:
                QuestionBean questionBean = new QuestionBean();
                if (MyApplication.getmLoginStatus() == 0) {
                    publishNoLoginError();
                } else {
                    mNewQuestionReleaseTextView.setTextColor(Color.parseColor("#61ffffff"));
                    questionBean.setAuthor(MyApplication.getmPersonBean());
                    questionBean.setTitle(mNewQuestionTitleEditText.getText().toString());
                    questionBean.setContent(mNewQuestionContentEditText.getText().toString());
                    mNewQuestionPresenter.newquestion(questionBean);
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
        if (!TextUtils.isEmpty(mNewQuestionTitleEditText.getText()) && !TextUtils.isEmpty(mNewQuestionContentEditText.getText())) {
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
