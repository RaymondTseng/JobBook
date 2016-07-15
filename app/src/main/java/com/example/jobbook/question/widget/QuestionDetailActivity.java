package com.example.jobbook.question.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.question.QuestionDetailListViewAdapter;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class QuestionDetailActivity extends Activity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initViews();
    }
    private void initViews(){
        mListView = (ListView) findViewById(R.id.question_detail_lv);
        mListView.setAdapter(new QuestionDetailListViewAdapter(this));
        Util.setListViewHeightBasedOnChildren(mListView);
    }
}
