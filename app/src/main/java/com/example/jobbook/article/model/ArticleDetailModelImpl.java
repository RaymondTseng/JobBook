package com.example.jobbook.article.model;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class ArticleDetailModelImpl implements ArticleDetailModel{
    @Override
    public void loadArticle(String url, OnLoadArticleListener mListener) {
        ArticleBean mArticle = new ArticleBean();
        mListener.onSucess(mArticle);
    }

    @Override
    public void loadComments(String url, OnLoadArticleCommentListener mListener) {
        List<ArticleCommentBean> mComments = new ArrayList<>();
        mListener.onSucess(mComments);
    }
    public interface OnLoadArticleListener{
        void onSucess(ArticleBean mArticle);
        void onFailure(String msg, Exception e);
    }
    public interface OnLoadArticleCommentListener{
        void onSucess(List<ArticleCommentBean> mComments);
        void onFailure(String msg, Exception e);
    }
}
