package com.example.jobbook.presenter.person;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.person.FavouriteArticleContract;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2018/1/26.
 */

public class FavouriteArticlePresenter extends RxPresenter<FavouriteArticleContract.View> implements FavouriteArticleContract.Presenter {

    public FavouriteArticlePresenter(FavouriteArticleContract.View view) {
        attach(view);
    }

    @Override
    public void loadArticles(String account) {
        addSubscribe(RetrofitService.loadFavouriteArticles(account)
                .subscribeWith(new BaseSubscriber<List<ArticleBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeans) {
                        mView.loadArticles(articleBeans);
                    }
                }));
    }
}
