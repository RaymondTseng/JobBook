package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.network.RetrofitService;
import com.example.jobbook.person.view.FavouriteArticleView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<ArticleBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.showLoadFailMsg(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeens) {
                        mView.hideProgress();
                        mView.addArticles(articleBeens);
                    }
                });
    }
}
