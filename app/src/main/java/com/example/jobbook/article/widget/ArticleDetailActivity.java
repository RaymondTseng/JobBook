package com.example.jobbook.article.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.article.ArticleDetailListViewAdapter;
import com.example.jobbook.util.Util;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailActivity extends Activity{
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initViews();
    }
    private void initViews(){
        mListView = (ListView)findViewById(R.id.article_detail_lv);
        mListView.setAdapter(new ArticleDetailListViewAdapter(this));
        Util.setListViewHeightBasedOnChildren(mListView);
    }

}
