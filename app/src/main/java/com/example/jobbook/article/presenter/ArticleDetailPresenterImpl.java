package com.example.jobbook.article.presenter;

import com.example.jobbook.article.model.ArticleDetailModel;
import com.example.jobbook.article.model.ArticleDetailModelImpl;
import com.example.jobbook.article.view.ArticleDetailView;
import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;

import java.util.List;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class ArticleDetailPresenterImpl implements ArticleDetailPresenter,
        ArticleDetailModelImpl.OnLoadArticleListener, ArticleDetailModelImpl.OnLoadArticleCommentListener, ArticleDetailModelImpl.OnLikeArticleListener, ArticleDetailModelImpl.OnUnlikeArticleListener {
    private ArticleDetailModel mModel;
    private ArticleDetailView mView;

    public ArticleDetailPresenterImpl(ArticleDetailView mView){
        this.mView = mView;
        mModel = new ArticleDetailModelImpl();
    }
    @Override
    public void loadArticle(String articleId) {
        mView.showProgress();
        mModel.loadArticle(articleId, this);
    }

    @Override
    public void loadComments() {
//        mModel.loadComments(null, null);
    }

    @Override
    public void like(String articleId) {
        mModel.like(articleId, this);
    }

    @Override
    public void unlike(String articleId) {
        mModel.unlike(articleId, this);
    }

    @Override
    public void onSuccess(List<ArticleCommentBean> mComments) {
//        mView.ad
    }


    @Override
    public void onSuccess(ArticleBean mArticle) {
        mView.hideProgress();
        mView.addArticle(mArticle);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        //先判断文章是否加载成功，不成功直接失败，在判断评论是否加载成功
        mView.showLoadFailMsg();
        mView.hideProgress();
        mView.close();
    }

    @Override
    public void onLikeSuccess() {
        mView.hideProgress();
        mView.likeSuccess();
    }

    @Override
    public void onLikeArticleFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.likeError();
    }

    @Override
    public void onLikeArticleNoLoginError() {
        mView.hideProgress();
        mView.NoLoginError();
    }

    @Override
    public void onUnlikeSuccess() {
        mView.hideProgress();
        mView.unlikeSuccess();
    }

    @Override
    public void onUnlikeArticleFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.unlikeError();
    }

    @Override
    public void onUnlikeArticleNoLoginError() {
        mView.hideProgress();
        mView.NoLoginError();
    }
}
