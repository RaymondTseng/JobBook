package com.example.jobbook.article.presenter;

import com.example.jobbook.MyApplication;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.NetConstants;
import com.example.jobbook.network.RetrofitService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

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
                    public void onError(Throwable e) {
//                        Logger.e(throwable.getMessage(), throwable);
                        mView.hideProgress();
                        if (e instanceof SocketTimeoutException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else if (e instanceof ConnectException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else {
                            mView.showLoadFailMsg(e.getMessage());
                        }
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
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        if (e instanceof SocketTimeoutException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else if (e instanceof ConnectException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else {
                            mView.showLoadFailMsg(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(String response) {
                        mView.hideProgress();
                        mView.likeSuccess();
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
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Logger.e(throwable.getMessage(), throwable);
                        mView.hideProgress();
                        if (e instanceof SocketTimeoutException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else if (e instanceof ConnectException) {
                            mView.showLoadFailMsg(NetConstants.NETWORK_ERROR_WORD);
                        } else {
                            mView.showLoadFailMsg(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(String response) {
                        mView.hideProgress();
                        mView.unlikeSuccess();
                    }
                });
    }
}
