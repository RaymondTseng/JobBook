package com.example.jobbook.article.presenter;

import com.example.jobbook.article.model.ArticleModel;
import com.example.jobbook.article.model.ArticleModelImpl;
import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticlePresenterImpl implements ArticlePresenter, ArticleModelImpl.OnLoadArticlesListListener{

    private ArticleModel mArticleModel;
    private ArticleView mArticleView;

    public ArticlePresenterImpl(ArticleView view) {
        mArticleView = view;
        mArticleModel = new ArticleModelImpl();
    }

    @Override
    public void loadArticles(int pageIndex, int type) {
        mArticleView.showProgress();
        mArticleModel.loadArticles(pageIndex, type, this);
    }

    @Override
    public void onSuccess(List<ArticleBean> list) {
        mArticleView.hideProgress();
        mArticleView.addArticles(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mArticleView.hideProgress();
        mArticleView.showLoadFailMsg();
    }
}
