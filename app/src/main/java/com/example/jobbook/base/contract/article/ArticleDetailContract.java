package com.example.jobbook.base.contract.article;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.ArticleBean;

/**
 * Created by zhaoxuzhang on 2017/11/27.
 */

public interface ArticleDetailContract {

    interface View extends IBaseView {
        void like(String articleId);

        void unlike(String articleId);

//    void addComments(List<ArticleCommentBean> mComments);

        void addArticle(ArticleBean mArticle);

        void close();

        void likeSuccess();

        void unlikeSuccess();
    }

    interface Presenter extends IBasePresenter<View> {
        void loadArticle(String articleId);

        void loadComments();

        void like(String articleId, String account);

        void unlike(String articleId, String account);
    }
}
