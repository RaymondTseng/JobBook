package com.example.jobbook.article.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.article.presenter.ArticleDetailPresenter;
import com.example.jobbook.article.presenter.ArticleDetailPresenterImpl;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.util.L;
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
    private LinearLayout mLoadingLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initViews();
        initEvents();
    }

    private void initViews() {
        mBackImageButton = (ImageButton) findViewById(R.id.article_detail_back_ib);
        mListView = (ListView) findViewById(R.id.article_detail_lv);
        mReadingQuantityTextView = (TextView) findViewById(R.id.article_detail_readingquanity_tv);
        mArticleTitleTextView = (TextView) findViewById(R.id.article_detail_title_tv);
        mArticleContentTextView = (TextView) findViewById(R.id.article_detail_content_tv);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents(){
        Util.setListViewHeightBasedOnChildren(mListView);
        mBackImageButton.setOnClickListener(this);
        mPresenter = new ArticleDetailPresenterImpl(this);
        mArticleBean = (ArticleBean) getIntent().getExtras().getSerializable("article_detail");
        L.i("article_bean_activity", "123:" + mArticleBean.getArticle_id());
        mPresenter.loadArticle(mArticleBean.getArticle_id());
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
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
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {
        Util.showSnackBar(this, "干货读取错误，请重试！");
    }

    @Override
    public void close() {
        finish();
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
