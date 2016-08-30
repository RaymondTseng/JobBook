package com.example.jobbook.article.model;

/**
 * Created by Xu on 2016/7/5.
 */
public interface ArticleModel {

    void loadArticles(int pageIndex, int type, ArticleModelImpl.OnLoadArticlesListListener listener);
}
