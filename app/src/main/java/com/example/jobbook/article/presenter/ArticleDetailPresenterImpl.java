package com.example.jobbook.article.presenter;

import com.example.jobbook.app.MyApplication;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.base.BaseSubscriber;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.model.http.RetrofitService;

import io.reactivex.functions.Consumer;


/**
 * @author Xu
 */
public class ArticleDetailPresenterImpl implements ArticleDetailPresenter {
    private ArticleDetailView mView;

    public ArticleDetailPresenterImpl(ArticleDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadArticle(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getArticleDetail(articleId, account)
                .subscribe(new BaseSubscriber<ArticleBean>() {

                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        mView.addArticle(articleBean);
                    }
                });
    }

    @Override
    public void loadComments() {
    }

    @Override
    public void like(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.like(articleId, account)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                })
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.likeSuccess();
                    }
                });
    }

    @Override
    public void unlike(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.unlike(articleId, account)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public IBaseView getBaseView() {
                        return mView;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.unlikeSuccess();
                    }
                });
    }
}
