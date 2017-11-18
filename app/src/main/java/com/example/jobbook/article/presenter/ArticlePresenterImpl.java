package com.example.jobbook.article.presenter;

import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.network.BaseObserver;
import com.example.jobbook.network.RetrofitService;

import java.util.List;

/**
 * @author Xu
 */
public class ArticlePresenterImpl implements ArticlePresenter {

    private ArticleView mView;

    public ArticlePresenterImpl(ArticleView view) {
        mView = view;
    }

    @Override
    public void loadArticles(int pageIndex, int type) {
        RetrofitService.getArticlesList(type, pageIndex)
                .subscribe(new BaseObserver<List<ArticleBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return null;
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeans) {
                        mView.addArticles(articleBeans);
                    }
                });
    }

}
