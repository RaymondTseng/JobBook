package com.example.jobbook.person.presenter;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.person.model.FavouriteArticleModel;
import com.example.jobbook.person.model.FavouriteArticleModelImpl;
import com.example.jobbook.person.view.FavouriteArticleView;

import java.util.List;

/**
 * Created by root on 16-12-9.
 */

public class FavouriteArticlePresenterImpl implements FavouriteArticleModelImpl.OnLoadArticleListener,
        FavouriteArticlePresenter{
    private FavouriteArticleView mView;
    private FavouriteArticleModel mModel;

    public FavouriteArticlePresenterImpl(FavouriteArticleView mView){
        this.mView = mView;
        mModel = new FavouriteArticleModelImpl();
    }
    @Override
    public void onSuccess(List<ArticleBean> mData) {
        mView.hideProgress();
        mView.addArticles(mData);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mView.hideProgress();
        mView.showLoadFailMsg(msg);
    }

    @Override
    public void loadArticle(String account) {
        mView.showProgress();
        mModel.loadArticles(account, this);
    }
}
