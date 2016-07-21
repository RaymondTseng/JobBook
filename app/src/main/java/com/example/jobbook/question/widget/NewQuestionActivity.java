package com.example.jobbook.question.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobbook.R;
import com.example.jobbook.question.presenter.NewQuestionPresenter;
import com.example.jobbook.question.presenter.NewQuestionPresenterImpl;
import com.example.jobbook.question.view.NewQuestionView;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionActivity extends Activity implements NewQuestionView, View.OnClickListener, TextWatcher{

    private static int NO_CHANGE_COLOR = 0;
    private static int CHANGE_COLOR = 1;

    private ImageButton mCloseImageButton;
    private EditText mNewQuestionTitleEditText;
    private EditText mNewQuestionContentEditText;
    private TextView mNewQuestionReleaseTextView;
    private NewQuestionPresenter mNewQuestionPresenter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CHANGE_COLOR) {
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
    }

    private void initViews() {
        mCloseImageButton = (ImageButton) findViewById(R.id.activity_publish_close_ib);
        mCloseImageButton.setOnClickListener(this);
        mNewQuestionTitleEditText = (EditText) findViewById(R.id.activity_publish_question_title_et);
        mNewQuestionContentEditText = (EditText) findViewById(R.id.activity_publish_question_content_et);
        mNewQuestionReleaseTextView = (TextView) findViewById(R.id.activity_publish_question_release_tv);
        mNewQuestionTitleEditText.addTextChangedListener(this);
        mNewQuestionContentEditText.addTextChangedListener(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess() {
        mNewQuestionPresenter.newquestion(null);
    }

    @Override
    public void switch2Question() {

    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_publish_close_ib:
                close();
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
        if (!mNewQuestionTitleEditText.getText().toString().equals("") && !mNewQuestionContentEditText.getText().toString().equals("")) {
            Message message = new Message();
            message.what = CHANGE_COLOR;
            handler.sendMessage(message);
        }else {
            Message message = new Message();
            message.what = NO_CHANGE_COLOR;
            handler.sendMessage(message);
        }
    }
}
