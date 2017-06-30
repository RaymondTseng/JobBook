package com.example.jobbook.article.presenter;

import com.example.jobbook.article.view.ArticleView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.network.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Xu on 2016/7/5.
 */
public class ArticlePresenterImpl implements ArticlePresenter {

//    private ArticleModel mArticleModel;
    private ArticleView mArticleView;

    public ArticlePresenterImpl(ArticleView view) {
        mArticleView = view;
//        mArticleModel = new ArticleModelImpl();
    }

    @Override
    public void loadArticles(int pageIndex, int type) {
//        mArticleView.showProgress();
//        mArticleModel.loadArticles(pageIndex, type, this);

        RetrofitService.getArticlesList(type, pageIndex)
//                .compose(mTransformer)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mArticleView.showProgress();
                    }
                })
                .subscribe(new Subscriber<List<ArticleBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.getMessage(), throwable);
                        mArticleView.showLoadFailMsg();
                    }

                    @Override
                    public void onNext(List<ArticleBean> list) {
                        mArticleView.hideProgress();
                        mArticleView.addArticles(list);
                    }
                });

    }

//    @Override
//    public void onSuccess(List<ArticleBean> list) {
////        mArticleView.hideProgress();
////        mArticleView.addArticles(list);
//    }
//
//    @Override
//    public void onFailure(String msg, Exception e) {
////        mArticleView.hideProgress();
////        mArticleView.showLoadFailMsg();
//    }

    /**
     * 统一变换
     */
    private Observable.Transformer<ArticleBean, List<ArticleBean>> mTransformer = new Observable.Transformer<ArticleBean, List<ArticleBean>>() {

        @Override
        public Observable<List<ArticleBean>> call(Observable<ArticleBean> articleBeanObservable) {
            return articleBeanObservable
                    .toList();
        }
    };
}
