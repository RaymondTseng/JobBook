package com.example.jobbook.article.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.jobbook.R;
import com.example.jobbook.article.presenter.ArticleDetailPresenterImpl;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailActivity extends Activity implements ArticleDetailView{
    private ListView mListView;
    private ArticleDetailPresenterImpl mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initViews();
    }
    private void initViews(){
        mListView = (ListView)findViewById(R.id.article_detail_lv);
        Util.setListViewHeightBasedOnChildren(mListView);
        mPresenter = new ArticleDetailPresenterImpl(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void addComments(List<ArticleCommentBean> mComments) {
//        mListView.setAdapter(new ArticleDetailListViewAdapter(this));
    }

    @Override
    public void addArticle(ArticleBean mArticle) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {

    }
}
