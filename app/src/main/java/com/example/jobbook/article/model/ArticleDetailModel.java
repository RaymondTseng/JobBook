package com.example.jobbook.article.model;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface ArticleDetailModel {
    void loadArticle(String articleId, ArticleDetailModelImpl.OnLoadArticleListener listener);
    void loadComments(String url, ArticleDetailModelImpl.OnLoadArticleCommentListener listener);
}
