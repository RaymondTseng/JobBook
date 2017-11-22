package com.example.jobbook.person.presenter;

import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.base.BaseObserver;
import com.example.jobbook.model.http.RetrofitService;
import com.example.jobbook.person.view.FavouriteArticleView;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteArticlePresenterImpl implements FavouriteArticlePresenter {
    private FavouriteArticleView mView;

    public FavouriteArticlePresenterImpl(FavouriteArticleView mView) {
        this.mView = mView;
    }

    @Override
    public void loadArticle(String account) {
        RetrofitService.loadFavouriteArticles(account)
                .subscribe(new BaseObserver<List<ArticleBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeans) {
                        mView.addArticles(articleBeans);
                    }
                });
    }
}
