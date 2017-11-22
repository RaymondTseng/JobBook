package com.example.jobbook.article.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface ArticleDetailView extends IBaseView {

    void like(String articleId);

    void unlike(String articleId);

//    void addComments(List<ArticleCommentBean> mComments);

    void addArticle(ArticleBean mArticle);

    void close();

    void likeSuccess();

    void unlikeSuccess();

}
