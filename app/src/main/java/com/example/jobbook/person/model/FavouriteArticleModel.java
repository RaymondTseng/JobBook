package com.example.jobbook.person.model;

/**
 * Created by root on 16-12-9.
 */

public interface FavouriteArticleModel {

    void loadArticles(String account, FavouriteArticleModelImpl.OnLoadArticleListener listener);
}
