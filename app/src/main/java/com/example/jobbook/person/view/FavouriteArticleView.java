package com.example.jobbook.person.view;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;


import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public interface FavouriteArticleView extends IBaseView {

    void addArticles(List<ArticleBean> mArticles);

}
