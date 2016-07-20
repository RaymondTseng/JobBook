package com.example.jobbook.feedback.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.feedback.presenter.FeedBackPresenter;
import com.example.jobbook.feedback.presenter.FeedBackPresenterImpl;
import com.example.jobbook.feedback.view.FeedBackView;

/**
 * Created by Xu on 2016/7/17.
 */
public class FeedBackActivity extends Activity implements FeedBackView, View.OnClickListener {

    private ImageButton mBackImageButton;
    private TextView mFeedBackTextView;
    private EditText mFeedBackMailEditText;
    private EditText mFeedBackContentEditText;
    private FeedBackPresenter mFeedBackPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_feedback);
        mFeedBackPresenter = new FeedBackPresenterImpl(this);
        initViews();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.suggestion_feedback_back_ib);
        mFeedBackTextView = (TextView) findViewById(R.id.suggestion_feedback_new_tv);
        mFeedBackMailEditText = (EditText) findViewById(R.id.suggestion_feedback_mail_et);
        mFeedBackContentEditText = (EditText) findViewById(R.id.suggestion_feedback_content_et);
        mFeedBackTextView.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void switch2Main() {

    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.suggestion_feedback_back_ib:
                back();
                break;
            case R.id.suggestion_feedback_new_tv:
                mFeedBackPresenter.feedback(mFeedBackMailEditText.getText().toString(), mFeedBackContentEditText.getText().toString());
                break;
        }
    }
}
