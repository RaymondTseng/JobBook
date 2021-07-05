package com.example.jobbook.base.contract.person;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;

import java.util.List;

/**
 * Created by Xu on 2017/12/17.
 */

public interface FavouriteArticleContract {

    interface View extends IBaseView {
        void loadArticles(List<ArticleBean> mArticles);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadArticles(String account);
    }
}
