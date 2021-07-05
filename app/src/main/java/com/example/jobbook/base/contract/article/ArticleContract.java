package com.example.jobbook.base.contract.article;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;

import java.util.List;

/**
 * Created by zhaoxuzhang on 2017/11/27.
 */

public interface ArticleContract {

    interface View extends IBaseView {
        void addArticles(List<ArticleBean> articlesList);
    }

    interface Presenter extends IBasePresenter<View> {
        void loadArticles(int pageIndex, int type);
    }
}
