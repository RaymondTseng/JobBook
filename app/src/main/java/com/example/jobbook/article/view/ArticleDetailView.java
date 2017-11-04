package com.example.jobbook.article.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface ArticleDetailView extends IBaseView {

    void like(String articleId);

    void unlike(String articleId);

    void addComments(List<ArticleCommentBean> mComments);

    void addArticle(ArticleBean mArticle);

    void close();

    void NoLoginError();

    void likeSuccess();

    void unlikeSuccess();

    void likeError();

    void unlikeError();

}
