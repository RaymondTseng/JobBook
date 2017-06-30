package com.example.jobbook.article.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.network.RetrofitService;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class ArticleDetailPresenterImpl implements ArticleDetailPresenter {
    private ArticleDetailView mView;

    public ArticleDetailPresenterImpl(ArticleDetailView mView) {
        this.mView = mView;
    }

    @Override
    public void loadArticle(String articleId) {
//        mView.showProgress();
//        mModel.loadArticle(articleId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.getArticleDetail(articleId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ArticleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.getMessage(), throwable);
                        mView.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        mView.hideProgress();
                        mView.addArticle(articleBean);
                    }
                });
    }

    @Override
    public void loadComments() {
    }

    @Override
    public void like(String articleId) {
        //        mModel.like(articleId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.like(articleId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.getMessage(), throwable);
                        mView.hideProgress();
                        mView.likeError();
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mView.hideProgress();
                            mView.likeSuccess();
                        } else {
                            if (resultBean.getResponse().equals("please login first!")) {
                                mView.hideProgress();
                                mView.NoLoginError();
                            } else if (resultBean.getResponse().equals("like failed")) {
                                mView.hideProgress();
                                mView.likeError();
                            }
                        }
                    }
                });
    }

    @Override
    public void unlike(String articleId) {
//        mModel.unlike(articleId, this);
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        RetrofitService.unlike(articleId, account)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribe(new Subscriber<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.getMessage(), throwable);
                        mView.hideProgress();
                        mView.unlikeError();
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (resultBean.getStatus().equals("true")) {
                            mView.hideProgress();
                            mView.unlikeSuccess();
                        } else {
                            if (resultBean.getResponse().equals("please login first!")){
                                mView.hideProgress();
                                mView.NoLoginError();
                            } else if (resultBean.getResponse().equals("like failed")) {
                                mView.hideProgress();
                                mView.unlikeError();
                            }
                        }
                    }
                });
    }
}
