package com.example.jobbook.presenter.article;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.base.RxPresenter;
import com.example.jobbook.base.contract.article.ArticleDetailContract;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.http.RetrofitService;

/**
 * Created by zhaoxuzhang on 2017/11/28.
 */

public class ArticleDetailPresenter extends RxPresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    public ArticleDetailPresenter(ArticleDetailContract.View view) {
        attach(view);
    }

    @Override
    public void loadArticle(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        addSubscribe(RetrofitService.getArticleDetail(articleId, account)
                .subscribeWith(new BaseSubscriber<ArticleBean>() {

                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        mView.addArticle(articleBean);
                    }
                })
        );
    }

    @Override
    public void loadComments() {

    }

    @Override
    public void like(String articleId, String account) {
        addSubscribe(RetrofitService.like(articleId, account)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.likeSuccess();
                    }
                })
        );
    }

    @Override
    public void unlike(String articleId, String account) {
        addSubscribe(RetrofitService.unlike(articleId, account)
                .subscribeWith(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.unlikeSuccess();
                    }
                })
        );
    }
}