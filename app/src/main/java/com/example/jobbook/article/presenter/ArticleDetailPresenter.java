package com.example.jobbook.article.presenter;

/**
 * Created by 椰树 on 2016/7/16.
 */
public interface ArticleDetailPresenter {
    void loadArticle(String articleId);

    void loadComments();

    void like(String articleId);

    void unlike(String articleId);
}
