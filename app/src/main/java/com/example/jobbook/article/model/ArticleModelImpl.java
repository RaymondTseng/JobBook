package com.example.jobbook.article.model;

import com.example.jobbook.bean.ArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticleModelImpl implements ArticleModel{

    @Override
    public void loadArticles(String url, OnLoadArticlesListListener listener) {
        // 测试数据
        List<ArticleBean> articles = new ArrayList<>();
        listener.onSuccess(articles);
    }

    public interface OnLoadArticlesListListener {
        void onSuccess(List<ArticleBean> list);
        void onFailure(String msg, Exception e);
    }

}
