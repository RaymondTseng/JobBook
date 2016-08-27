package com.example.jobbook.article.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.article.presenter.ArticleDetailPresenter;
import com.example.jobbook.article.presenter.ArticleDetailPresenterImpl;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.util.Util;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailActivity extends Activity implements ArticleDetailView, View.OnClickListener {
    private ListView mListView;
    private ArticleDetailPresenter mPresenter;
    private ImageButton mBackImageButton;
    private ArticleBean mArticleBean;
    private TextView mReadingQuantityTextView;
    private TextView mArticleTitleTextView;
    private TextView mArticleContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initViews();
        mPresenter = new ArticleDetailPresenterImpl(this);
        mArticleBean = (ArticleBean) getIntent().getExtras().getSerializable("article_detail");
        Log.i("article_bean_activity", "123:" + mArticleBean.getArticle_id());
        mPresenter.loadArticle(mArticleBean.getArticle_id());
//        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
//        mNewsDetailPresenter.loadNewsDetail(mNews.getDocid());
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.article_detail_back_ib);
        mListView = (ListView) findViewById(R.id.article_detail_lv);
        Util.setListViewHeightBasedOnChildren(mListView);
        mReadingQuantityTextView = (TextView) findViewById(R.id.article_detail_readingquanity_tv);
        mArticleTitleTextView = (TextView) findViewById(R.id.article_detail_title_tv);
        mArticleContentTextView = (TextView) findViewById(R.id.article_detail_content_tv);
        mBackImageButton.setOnClickListener(this);
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
        mReadingQuantityTextView.setText(mArticle.getReadingquantity() + "跟帖");
        mArticleTitleTextView.setText(mArticle.getTitle());
        mArticleContentTextView.setText(mArticle.getContent());
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadFailMsg() {
//        final Snackbar snackbar = Snackbar.make(, "干货读取错误，请重试！", Snackbar.LENGTH_LONG);
//        snackbar.setAction("dismiss", new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                snackbar.dismiss();
//            }
//        });
//        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_detail_back_ib:
                finish();
                break;
        }
    }
}
