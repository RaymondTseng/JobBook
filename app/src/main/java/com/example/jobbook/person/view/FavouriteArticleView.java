package com.example.jobbook.person.view;

import com.example.jobbook.bean.ArticleBean;


import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public interface FavouriteArticleView {

    void showProgress();

    void addArticles(List<ArticleBean> mArticles);

    void hideProgress();

    void showLoadFailMsg(String msg);

}
