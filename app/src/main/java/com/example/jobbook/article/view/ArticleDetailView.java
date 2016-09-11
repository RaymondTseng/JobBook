package com.example.jobbook.article.view;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface ArticleDetailView {

    void showProgress();

    void addComments(List<ArticleCommentBean> mComments);

    void addArticle(ArticleBean mArticle);

    void hideProgress();

    void showLoadFailMsg();

    void close();

}
