package com.example.jobbook.article.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.article.presenter.ArticleDetailPresenter;
import com.example.jobbook.article.presenter.ArticleDetailPresenterImpl;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailActivity extends Activity implements ArticleDetailView, View.OnClickListener {
    private ListView mListView;
    private ArticleDetailPresenter mPresenter;
    private ImageButton mLikeImageButton;
    private ImageButton mBackImageButton;
    private ArticleBean mArticleBean;
    private TextView mReadingQuantityTextView;
    private TextView mArticleTitleTextView;
    private TextView mTimeTextView;
    private HtmlTextView mArticleContentTextView;
    private ViewStub mLoadingViewStub;
    private ArticleBean bean;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        view = getWindow().getDecorView();
        initViews();
        initEvents();
    }

    private void initViews() {
        mLikeImageButton = (ImageButton) findViewById(R.id.article_detail_like_ib);
        mBackImageButton = (ImageButton) findViewById(R.id.article_detail_back_ib);
//        mListView = (ListView) findViewById(R.id.article_detail_lv);
        mReadingQuantityTextView = (TextView) findViewById(R.id.article_detail_content_reading_tv);
        mArticleTitleTextView = (TextView) findViewById(R.id.article_detail_title_tv);
        mArticleContentTextView = (HtmlTextView) findViewById(R.id.article_detail_content_tv);
        mTimeTextView = (TextView) findViewById(R.id.article_detail_content_time_tv);
        mLoadingViewStub = (ViewStub) findViewById(R.id.article_detail_loading);
        mLoadingViewStub.inflate();
    }

    private void initEvents() {
        Util.setListViewHeightBasedOnChildren(mListView);
        mLikeImageButton.setOnClickListener(this);
        mBackImageButton.setOnClickListener(this);
        mPresenter = new ArticleDetailPresenterImpl(this);
        mArticleBean = (ArticleBean) getIntent().getExtras().getSerializable("article_detail");
        mPresenter.loadArticle(mArticleBean.getArticle_id());
    }

    @Override
    public void showProgress() {
        mLoadingViewStub.setVisibility(View.VISIBLE);
    }

    @Override
    public void addComments(List<ArticleCommentBean> mComments) {
//        mListView.setAdapter(new ArticleDetailListViewAdapter(this));
    }

    @Override
    public void addArticle(ArticleBean mArticle) {
        bean = mArticle;
        mReadingQuantityTextView.setText(mArticle.getReadingquantity() + "");
        mArticleTitleTextView.setText(mArticle.getTitle());
//        mArticleContentTextView.setText(mArticle.getContent());
        mArticleContentTextView.setHtml(StringEscapeUtils.unescapeHtml4(mArticle.getContent()), new HtmlHttpImageGetter(mArticleContentTextView));
        mTimeTextView.setText(mArticle.getDate());
        if (MyApplication.getmLoginStatus() == 1) {
            L.i("like_status", bean.isIfLike() + "");
            if (bean.isIfLike() == 0) {
                mLikeImageButton.setImageResource(R.mipmap.favourite);
            } else {
                mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
            }
        }
    }

    @Override
    public void hideProgress() {
        mLoadingViewStub.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {
        Util.showSnackBar(view, "干货读取错误，请重试！");
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void NoLoginError() {
        Util.showSnackBar(view, "请先登录");
    }

    @Override
    public void likeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
        Util.showSnackBar(view, "收藏成功！");
    }

    @Override
    public void unlikeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite);
        Util.showSnackBar(view, "取消收藏成功！");
    }

    @Override
    public void likeError() {
        Util.showSnackBar(view, "收藏失败，请重试！");
    }

    @Override
    public void unlikeError() {
        Util.showSnackBar(view, "取消收藏失败，请重试！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_detail_back_ib:
                finish();
                break;
            case R.id.article_detail_like_ib:
                L.i("like_ib_click", "status:" + bean.isIfLike());
                if (bean.isIfLike() == 0) {
                    L.i("like_ib_click", "click like");
//                    mLikeImageButton.setImageResource(R.mipmap.favourite);
                    like(bean.getArticle_id());

                } else {
                    L.i("like_ib_click", "click unlike");
//                    mLikeImageButton.setImageResource(R.mipmap.favourite_white);
                    unlike(bean.getArticle_id());
                }
                refresh();
                break;
        }
    }

    @Override
    public void like(String articleId) {
        mPresenter.like(articleId);
    }

    @Override
    public void unlike(String articleId) {
        mPresenter.unlike(articleId);
    }

    private void refresh() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(null);
    }
}
