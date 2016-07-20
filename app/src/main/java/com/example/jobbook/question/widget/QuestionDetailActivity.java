package com.example.jobbook.question.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.bean.QuestionBean;
import com.example.jobbook.bean.QuestionCommentBean;
import com.example.jobbook.question.QuestionDetailListViewAdapter;
import com.example.jobbook.question.view.QuestionDetailView;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class QuestionDetailActivity extends Activity implements QuestionDetailView, View.OnClickListener{
    private ListView mListView;
    private EditText mEditText;
    private ImageButton mSendImageButton;
    private ImageButton mBackImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initViews();
    }
    private void initViews(){
        mListView = (ListView) findViewById(R.id.question_detail_lv);
        mEditText = (EditText) findViewById(R.id.question_detail_et);
        mSendImageButton = (ImageButton) findViewById(R.id.question_detail_send_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.question_detail_back_ib);
        mBackImageButton.setOnClickListener(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addQuestion(QuestionBean mQuestion) {

    }

    @Override
    public void addComments(List<QuestionCommentBean> mComments) {
//        mListView.setAdapter(new QuestionDetailListViewAdapter(this));
//        Util.setListViewHeightBasedOnChildren(mListView);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public String getComment() {
        return mEditText.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_detail_back_ib:
                finish();
                break;
        }
    }
}
