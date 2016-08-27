package com.example.jobbook.article.model;

import android.util.Log;

import com.example.jobbook.bean.ArticleBean;
import com.example.jobbook.bean.ArticleCommentBean;
import com.example.jobbook.commons.Urls;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 椰树 on 2016/7/16.
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {

    @Override
    public void loadArticle(String articleId, final OnLoadArticleListener listener) {
        OkHttpUtils.postString().url(Urls.ARTICLE_DETAIL_URL + articleId).content(articleId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onFailure("network error", e);
            }

            @Override
            public void onResponse(String response, int id) {
                ArticleBean articleBean = new Gson().fromJson(response, ArticleBean.class);
                listener.onSuccess(articleBean);
            }
        });
    }

    @Override
    public void loadComments(String url, OnLoadArticleCommentListener mListener) {
        List<ArticleCommentBean> mComments = new ArrayList<>();
        mListener.onSuccess(mComments);
    }

    public interface OnLoadArticleListener {
        void onSuccess(ArticleBean mArticle);

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadArticleCommentListener {
        void onSuccess(List<ArticleCommentBean> mComments);

        void onFailure(String msg, Exception e);
    }
}
