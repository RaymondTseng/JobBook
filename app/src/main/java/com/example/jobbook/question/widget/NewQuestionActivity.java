package com.example.jobbook.question.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.question.presenter.NewQuestionPresenter;
import com.example.jobbook.question.presenter.NewQuestionPresenterImpl;
import com.example.jobbook.question.view.NewQuestionView;

/**
 * Created by Xu on 2016/7/16.
 */
public class NewQuestionActivity extends Activity implements NewQuestionView{

    private ImageButton mCloseImageButton;
    private EditText mNewQuestionTitleEditText;
    private EditText mNewQuestionContentEditText;
    private TextView mNewQuestionReleaseTextView;
    private NewQuestionPresenter mNewQuestionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        mNewQuestionPresenter = new NewQuestionPresenterImpl(this);
        initViews();
    }

    private void initViews() {
        mCloseImageButton = (ImageButton) findViewById(R.id.activity_publish_close_ib);
        mNewQuestionTitleEditText = (EditText) findViewById(R.id.activity_publish_question_title_et);
        mNewQuestionContentEditText = (EditText) findViewById(R.id.activity_publish_question_content_et);
        mNewQuestionReleaseTextView = (TextView) findViewById(R.id.activity_publish_question_release_tv);
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

}
