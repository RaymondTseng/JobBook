package com.example.jobbook.article.view;

import com.example.jobbook.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public interface ArticleView {

    void showProgress();

    void addArticles(List<ArticleBean> articlesList);

    void hideProgress();

    void showLoadFailMsg();

}
