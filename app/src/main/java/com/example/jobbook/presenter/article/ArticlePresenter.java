package com.example.jobbook.presenter.article;

import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.article.ArticleContract;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.http.RetrofitService;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/11/27.
 */

public class ArticlePresenter extends RxPresenter<ArticleContract.View> implements ArticleContract.Presenter {

    public ArticlePresenter(ArticleContract.View view) {
        attach(view);
    }

    @Override
    public void loadArticles(int pageIndex, int type) {
        addSubscribe(RetrofitService.getArticlesList(type, pageIndex)
                .subscribeWith(new BaseSubscriber<List<ArticleBean>>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(List<ArticleBean> articleBeans) {
                        mView.addArticles(articleBeans);
                    }
                })
        );

    }
}
