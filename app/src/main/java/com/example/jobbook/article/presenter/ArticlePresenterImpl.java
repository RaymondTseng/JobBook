package com.example.jobbook.article.presenter;

import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.commons.NetConstants;
import com.example.jobbook.network.RetrofitService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticlePresenterImpl implements ArticlePresenter {

//    private ArticleModel mArticleModel;
    private ArticleView mView;

    public ArticlePresenterImpl(ArticleView view) {
        mView = view;
//        mArticleModel = new ArticleModelImpl();
    }

    @Override
    public void loadArticles(int pageIndex, int type) {
//        mView.showProgress();
//        mArticleModel.loadArticles(pageIndex, type, this);

        RetrofitService.getArticlesList(type, pageIndex)
//                .compose(mTransformer)
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
                    public void onNext(List<ArticleBean> list) {
                        mView.hideProgress();
                        mView.addArticles(list);
                    }
                });

    }

}
